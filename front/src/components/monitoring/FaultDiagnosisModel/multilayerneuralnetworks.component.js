import React, { Component } from "react";

export default class MultilayerNeuralNetworks extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: ""
    };
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
        다층신경망
        </header>
      </div>
    );
  }
}
