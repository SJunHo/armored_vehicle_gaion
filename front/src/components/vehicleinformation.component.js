import React, { Component } from "react";

export default class VehicleInformation extends Component {
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
        차량정보 조회
        </header>
      </div>
    );
  }
}
