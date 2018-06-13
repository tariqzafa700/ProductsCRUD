import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {  Link } from "react-router-dom";

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
    display: 'flex',
    justifyContent: 'center',
    flexDirection: 'column',
    alignItems: 'center'
  },
  table: {
    width: 400
  },
  paper: {
    width: 600,
  }
});
  
const onDelete = id => {
  fetch("/products/"+id, {
    method: "DELETE",
    headers: {
      'Accept': 'application/json'
    },
    credentials: "same-origin"
  }).then(function(response) {
    let read = response.text();
    return read;
  }).then((text) => {
  });
}

const UpdateDeleteColumn = props => { 
  return (
    <td style={{display: 'flex', flexDirection:'row', paddingTop: 5}}>
      <Link to={{pathname: "/home", state: {product: props.rowData} }}>Update</Link>
      <Link style={{paddingLeft: 5}} to={{pathname: "/list", state: {doUpdate: true} }} onClick = {() => {onDelete(props.rowData.productId)}}>Delete</Link>
    </td>
)};
  
export class ProductList extends Component {
  constructor(props) {
    super(props);
    this.state = {
        data: [],
        doUpdate: false
    };
  }

  componentDidMount = () => {
    this.getAllData();
  }
  
  componentWillReceiveProps = newProps => {
    setTimeout(() => {
      this.getAllData();
    }, 100);
  }

  getAllData = () => {
    fetch("/products", {
      method: "GET",
      headers: {
        'Accept': 'application/json'
      },
      credentials: "same-origin"
    }).then(function(response) {
      let read = response.text();
      return read;
    }).then((text) => {
      this.setState({data: JSON.parse(text)});
    });
  }
  
  render () {
    const { classes } = this.props;
    const { data } = this.state;

    return(
      <div className={classes.root}>
      <Paper className={classes.paper}>
      <Table className={classes.table}>
      <TableHead>
        <TableRow>
          <TableCell numeric>Product Id</TableCell>
          <TableCell>Product Name</TableCell>
          <TableCell numeric>Quantity</TableCell>
          <TableCell>Actions</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {data.map(n => {
          return (
              <TableRow key={n.productId}>
                <TableCell numeric>{n.productId}</TableCell>
                <TableCell component="th" scope="row">
                  {n.name}
                </TableCell>
                <TableCell numeric>{n.quantity}</TableCell>
                <TableCell scope="row"><UpdateDeleteColumn rowData={n}/></TableCell>
              </TableRow>
          );
        })}
      </TableBody>
      </Table>
      </Paper>
      <Link style = {{marginTop: 10}} to={{pathname: "/home", state: {product: null} }}>Home</Link>
      </div>
    );
  }
}

export default withStyles(styles)(ProductList);
