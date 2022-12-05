import React, { Component } from "react";

export default class IsolateRamdhamForest extends Component {
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
        Isolate 랜덤 포레스트
        </header>
      </div>
    );
  }
}
