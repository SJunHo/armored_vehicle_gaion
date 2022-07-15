import React, { Component } from "react";

export default class DriverCalibration extends Component {
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
        운전자 교정 정보
        </header>
      </div>
    );
  }
}
