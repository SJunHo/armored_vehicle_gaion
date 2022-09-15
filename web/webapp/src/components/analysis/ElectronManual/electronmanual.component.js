import React, { Component } from "react";

export default class ElectronManual extends Component {
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
        전자 매뉴얼
        </header>
      </div>
    );
  }
}
