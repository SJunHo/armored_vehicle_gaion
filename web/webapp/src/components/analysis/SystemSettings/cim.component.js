import React, { Component } from "react";
import cimService from "../../../services/analysis/cim.service";

export default class CIM extends Component {
  constructor(props) {
    super(props);
    this.getCmncd = this.getCmncd.bind(this);
    this.saveCmncd = this.saveCmncd.bind(this);
    this.deleteCmncd = this.deleteCmncd.bind(this);

    this.state = {
      cmncdid : "",
      changedCode : "",
      changedGroupcode : "",
      changedExpln : "",
      changedUsedvcd : "",
      changedVar : "",
    };
  }

  componentDidMount(){
    this.getCmncd(this.props.match.params.id);
  }

  getCmncd(id){
    cimService.get(id)
    .then((response) => {
      console.log(response);
      this.setState({
        cmncdid : response.data.cmncdid,
        changedCode : response.data.code,
        changedGroupcode : response.data.groupcode,
        changedExpln : response.data.expln,
        changedUsedvcd : response.data.usedvcd,
        changedVar : response.data.var,
      });
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  saveCmncd(){
    var data = {
        cmncdid : this.state.cmncdid,
        code : this.state.changedCode,
        expln : this.state.changedExpln,
        usedvcd : this.state.changedUsedvcd,
        var : this.state.changedVar,
        groupcode : this.state.changedGroupcode,
    };

    cimService.update(data)
    .then((reponse) => {
        console.log(reponse);
        window.location.href = "/cimList";
        })
        .catch((e) => {
        console.log(e);
        });
  } 

  deleteCmncd(){
    var result = window.confirm("정말 삭제하시겠습니까?");
    if(result){
      cimService.delete(this.state.cmncdid)
      .then((reponse) => {
        alert("삭제되었습니다");
        window.location.href = "/cimList";
        })
        .catch((e) => {
        console.log(e);
        });
    }else{
      return;
    }
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
        공통정보 하나
        </header>   
        <div className="contents02">
            <div className="form-group">
              <label htmlFor="group">그룹</label>
              <input
                type="text"
                className="form-control-style"
                id="group"
                required
                defaultValue={this.state.changedGroupcode}
                onChange={(e)=> this.setState({changedGroupcode : e.target.value})}
                name="group"
              />
            </div>

            <div className="form-group">
              <label htmlFor="code">코드</label>
              <input
                type="text"
                className="form-control-style"
                id="code"
                required
                defaultValue={this.state.changedCode}
                onChange={(e)=> this.setState({changedCode : e.target.value})}
                name="code"
              />
            </div>

            <div className="form-group">
              <label htmlFor="var">값</label>
              <input
                type="text"
                className="form-control-style"
                id="var"
                required
                defaultValue={this.state.changedVar}
                onChange={(e)=> this.setState({changedVar : e.target.value})}
                name="var"
              />
            </div>

            <div className="form-group">
              <label htmlFor="expln">설명</label>
              <input
                type="text"
                className="form-control-style"
                id="expln"
                required
                defaultValue={this.state.changedExpln}
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
            <button onClick={this.deleteCmncd} className="btn btn04 btn-danger">
              삭제
            </button>
            <button onClick={this.saveCmncd} className="btn btn04 btn-success mr20">
              수정
            </button>
          </div>   
      </div>
    );
  }
}
