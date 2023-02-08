import React, { Component } from "react";

export default class ElectronManual extends Component {
  constructor(props) {
    super(props);
    this.state = {
      info : [19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34]
    };
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
         전자 매뉴얼
        </header>
        <div>
        {this.state.info.map((name,index)=> (
          <div key={index} className="manual_div">
            <img className="manual_image" src={require("./manual/manual-info-"+name+".jpg")} />
          </div>
          ))
        }
        </div>
      </div>
    );
  }
}
