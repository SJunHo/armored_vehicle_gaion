import React, { Component } from "react";
import cimService from "../../../services/analysis/cim.service";


export default class AddCIM extends Component {
  constructor(props) {
    super(props);

    this.saveCmncd = this.saveCmncd.bind(this);
    this.state = {
        changedCode : "",
        changedGroupcode : "",
        changedExpln : "",
        changedUsedvcd : "",
        changedVar : "",
    };
  }
  componentDidUpdate(prevState,prevProp){
    if(prevState.changedUsedvcd !== this.state.changedUsedvcd){
        console.log(this.state.changedUsedvcd);
    }
  }
  saveCmncd(){
        var data = {
            code : this.state.changedCode,
            expln : this.state.changedExpln,
            usedvcd : this.state.changedUsedvcd,
            var : this.state.changedVar,
            groupcode : this.state.changedGroupcode
        };

        cimService.create(data)
          .then((reponse) => {
            alert("등록되었습니다");
              window.location.href = "/cimList";
              })
          .catch((e) => {
            alert("등록에 실패했습니다");
            console.log(e);
          });
    }
    

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
        공통정보 등록
        </header>  
        <div className="contents02">
            <div className="form-group">
              <label htmlFor="group">그룹</label>
              <input
                type="text"
                className="form-control form-control-style"
                id="group"
                required
                onChange={(e)=> this.setState({changedGroupcode : e.target.value})}
                name="group"
              />
            </div>

            <div className="form-group">
              <label htmlFor="code">코드</label>
              <input
                type="text"
                className="form-control form-control-style"
                id="code"
                required
                onChange={(e)=> this.setState({changedCode : e.target.value})}
                name="code"
              />
            </div>

            <div className="form-group">
              <label htmlFor="var">값</label>
              <input
                type="text"
                className="form-control form-control-style"
                id="var"
                required
                onChange={(e)=> this.setState({changedVar : e.target.value})}
                name="var"
              />
            </div>

            <div className="form-group">
              <label htmlFor="expln">설명</label>
              <input
                type="text"
                className="form-control form-control-style"
                id="expln"
                required
                onChange={(e)=> this.setState({changedExpln : e.target.value})}
                name="expln"
              />
            </div>
            <div className="form-group">
                <label htmlFor="usedvcd">사용여부</label>
                <div className="radio-box">
                  <div className="radio-use">
                    <input type="radio"
                      id="useY"
                      required
                      checked={this.state.changedUsedvcd === "Y"}
                      onChange={(e)=> this.setState({changedUsedvcd : 'Y'})}
                      name="usedvcd"
                    />
                    <label htmlFor="useY">사용</label>
                  </div>
                  <div className="radio-use">
                    <input type="radio"
                      id="useN"
                      required
                      checked={this.state.changedUsedvcd === "N"}
                      onChange={(e)=> this.setState({changedUsedvcd : 'N'})}
                      name="usedvcd"
                    />
                    <label htmlFor="useN">미사용</label>
                  </div>
                </div>
            </div>

            <button onClick={this.saveCmncd} className="btn btn04 btn-success">
              생성
            </button>
          </div>
      </div>
    );
  }
}

