import React, { Component } from "react";

export default class CIM extends Component {
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
        공통정보 관리
        </header>
      </div>
    );
  }
}
