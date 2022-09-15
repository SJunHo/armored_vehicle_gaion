import React, { Component } from "react";

export default class PartsReplacementCycle extends Component {
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
         부품 교환주기
        </header>
      </div>
    );
  }
}
