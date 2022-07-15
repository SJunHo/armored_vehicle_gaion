import React, { Component } from "react";

export default class OutliersVehicleInquiry extends Component {
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
         이상치 차량 조회
        </header>
      </div>
    );
  }
}
