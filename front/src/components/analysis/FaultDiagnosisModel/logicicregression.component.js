import React, { Component } from "react";

export default class LogicicRegession extends Component {
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
        로지스틱 회귀
        </header>
      </div>
    );
  }
}
