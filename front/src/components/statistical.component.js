import React, { Component } from "react";

export default class Statistical extends Component {
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
        통계정보 조회
        </header>
      </div>
    );
  }
}
