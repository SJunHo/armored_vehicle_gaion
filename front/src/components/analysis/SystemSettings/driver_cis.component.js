import React, { Component } from "react";

export default class Driver_cis extends Component {
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
        운전자 교정정보 설정
        </header>
      </div>
    );
  }
}
