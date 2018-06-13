import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import {  Redirect } from "react-router-dom";

const styles = theme => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap',
    flexDirection: 'column',
    justifyContent: "center",
    alignItems: 'center'
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200,
  },
  button: {
    marginTop: 20,
    borderRadius: '4px'
  }
});

export class ProductPurchase extends Component {
  constructor(props) {
    super(props);
    this.state = {
        name: "",
        quantity: "",
        redirect: false,
        doUpdate: false,
        errorMessage: undefined
    }
  }

  componentDidMount = () => {
    const { location } = this.props;
    this.sendUpdateRequest(location);
  }
  
  componentWillReceiveProps = newProps => {
    const { location } = newProps;
    this.sendUpdateRequest(location);
  }
  
  sendUpdateRequest = location => {
    if(location && location.state && location.state.product) {
      this.setState({productId: location.state.product.productId, 
        name: location.state.product.name, 
        quantity: location.state.product.quantity, 
        redirect: location.state.redirect, 
        doUpdate: true});
    }
  }
  
  handleChange = name => event => {
    this.setState({
      [name]: event.target.value,
    });
  };

  handleReset = evt => {
    evt.preventDefault();
    this.setState({productId: undefined, 
      name: "", 
      quantity: "", 
      redirect: false, 
      doUpdate: false
    });
  }
  
  handleSubmit = evt => {
    evt.preventDefault();
    const {name, quantity, doUpdate, productId} = this.state;
    if(!doUpdate) {
      fetch("/products", {
        method: "POST",
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        credentials: "same-origin",
        body: JSON.stringify({name: name, quantity: quantity})
      }).then(function(response) {
        return response.text();
      }).then((text) => {
        let obj = JSON.parse(text);
        if(obj.errorMessage) {
          this.setState({errorMessage: obj.errorMessage})
        } else {
          this.setState({redirect: true});
        }
      }).catch(error => {});
    } else {
      fetch("/products/"+productId, {
        method: "PUT",
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        credentials: "same-origin",
        body: JSON.stringify({name: name, quantity: quantity})
      }).then(function(response) {
        let read = response.text();
        return read;
      }).then((text) => {
        let obj = JSON.parse(text);
        if(obj.errorMessage) {
          this.setState({error: obj.errorMessage})
        } else {
          this.setState({doUpdate: false, redirect: true, productId: undefined});
        }
      }).catch(error => {});
    }
  }

	  render() {
	  const { classes } = this.props;
	  const { redirect, errorMessage } = this.state;
	  
	  if(redirect) {
	    return <Redirect to={"/list"}/>
	  }
	  return (
	      <div>
	        <form className={classes.container} onSubmit={this.handleSubmit}>
	          <div style={{color: 'red'}}>{errorMessage?errorMessage:null}</div>
	          <TextField
	            id="name"
	              ref={node => this.name=node}
	            label="Product Name"
	              className={classes.textField}
	            value={this.state.name}
	            onChange={this.handleChange('name')}
	            margin="normal"
	          />
	          <TextField
	            id="quantity"
	            ref={node => this.quantity=node}
	            label="Quantity"
	              className={classes.textField}
	            value={this.state.quantity}
	            onChange={this.handleChange('quantity')}
	            margin="normal"
	          />
	          <Button type="submit" variant="raised" color="primary" className={classes.button}>
	            Save
	          </Button>
	          <Button variant="raised" color="primary" className={classes.button} onClick={this.handleReset}>
	            Reset
	          </Button>
	        </form>
	      </div>
	    )
	  }
  }

export default withStyles(styles)(ProductPurchase);

