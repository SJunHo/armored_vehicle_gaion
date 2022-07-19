import React, { Component } from "react";

export default class LearningData extends Component {
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
        학습데이터
        </header>
      </div>
    );
  }
}
