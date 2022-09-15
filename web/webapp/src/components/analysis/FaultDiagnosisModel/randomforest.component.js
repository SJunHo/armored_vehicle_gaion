import React, { Component } from "react";

export default class RandomForest extends Component {
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
        랜덤포레스트
        </header>
      </div>
    );
  }
}
