import React, { Component } from "react";

export default class ElectronManual extends Component {
  constructor(props) {
    super(props);
    this.state = {
      info : [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22]
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
