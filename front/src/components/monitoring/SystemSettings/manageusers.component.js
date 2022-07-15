import React, { Component } from "react";

export default class ManageUsers extends Component {
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
        사용자 관리
        </header>
      </div>
    );
  }
}
