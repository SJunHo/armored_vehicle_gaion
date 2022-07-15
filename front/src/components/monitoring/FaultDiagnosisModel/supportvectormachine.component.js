import React, { Component } from "react";

export default class SupportVectorMachine extends Component {
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
        서포트 벡터 머신
        </header>
      </div>
    );
  }
}
