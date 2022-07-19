import React, { Component } from "react";

export default class LinearRegression extends Component {
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
        선형 회귀
        </header>
      </div>
    );
  }
}
