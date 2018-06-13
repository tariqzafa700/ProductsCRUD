import React, { Component } from 'react';
import { Provider } from "react-redux";
import {
  BrowserRouter as Router,
  Route,
} from "react-router-dom";
import store from "./store";
import ProductPurchase from "./layout/ProductPurchase";
import ProductList from "./layout/ProductList";
import './App.css';

class App extends Component {
  render() {
    return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <header className="App-header">
            <h1 className="App-title">Product Purchase</h1>
          </header>
          <Route path="/home" component={ProductPurchase} />
          <Route path="/list" component={ProductList} />
        </div>
      </Router>
    </Provider>
    );
  }
}

export default App;
