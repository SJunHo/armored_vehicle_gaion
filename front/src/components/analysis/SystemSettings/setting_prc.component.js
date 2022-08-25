import React, { Component } from "react";
import PartsReplacementCycleService from "../../../services/analysis/setting_prc.service";
import { connect } from "react-redux";

class Setting_PRC extends Component {
  constructor(props) {
    super(props);
    this.getPartsReplacementList = this.getPartsReplacementList.bind(this);
    this.onStdValChange = this.onStdValChange.bind(this);
    this.onPrdValChange = this.onPrdValChange.bind(this);
    this.onNmValChange = this.onNmValChange.bind(this);
    this.onMessageChange = this.onMessageChange.bind(this);
    this.updatePrcList = this.updatePrcList.bind(this);
    this.onApplicabilityChange = this.onApplicabilityChange.bind(this);

    const {user} = this.props; 
    this.state = {
      user : user,
      prcList : [],
    };
  }

  componentDidMount(){
    this.getPartsReplacementList();
  }

  getPartsReplacementList(){
    PartsReplacementCycleService.getList()
    .then((response) => {
      this.setState({
        prcList: response.data,
      });
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  onStdValChange(e){
    const {name, value} = e.target;
    let index = name.split('stdval')[1];
    let prcSet = this.state.prcList;
    prcSet[index].stdval = value;
    prcSet[index].mdfcdt = new Date();
    prcSet[index].mdfr = this.state.user.username;
    this.setState({
      prcList: prcSet
    });
    console.log(this.state.prcList);
  }

  onPrdValChange(e){
    const {name, value} = e.target;
    let index = name.split('prdval')[1];
    let prcSet = this.state.prcList;
    prcSet[index].prdval = value;
    prcSet[index].mdfcdt = new Date();
    prcSet[index].mdfr = this.state.user.username;
    this.setState({
      prcList: prcSet
    });
    console.log(this.state.prcList);
  }

  onNmValChange(e){
    const {name, value} = e.target;
    let index = name.split('nmval')[1];
    let prcSet = this.state.prcList;
    prcSet[index].nmval = value;
    prcSet[index].mdfcdt = new Date();
    prcSet[index].mdfr = this.state.user.username;
    this.setState({
      prcList: prcSet
    });
    console.log(this.state.prcList);
  }

  onMessageChange(e){
    const {name, value} = e.target;
    let index = name.split('msg')[1];
    let prcSet = this.state.prcList;
    prcSet[index].msg = value;
    prcSet[index].mdfcdt = new Date();
    prcSet[index].mdfr = this.state.user.username;
    this.setState({
      prcList: prcSet
    });
    console.log(this.state.prcList);
  }

  onApplicabilityChange(e){
    const {name, checked} = e.target;
    let index = name.split('checkbox')[1];
    let prcSet = this.state.prcList;
    prcSet[index].applicability = checked;
    prcSet[index].mdfcdt = new Date();
    prcSet[index].mdfr = this.state.user.username;
    this.setState({
      prcList: prcSet
    });
  }

  updatePrcList(){
    PartsReplacementCycleService.updateList(this.state.prcList)
    .then((response) => {
      console.log(response);
      if(response.status === 201){
        alert("정상등록되었습니다");
      }
    })
    .catch((e) => {
      console.log(e);
      alert("에러 발생");
    });
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
        부품 교환주기 설정
        </header>
        <div className="table-div table03">
        <table>
          <thead>
          <tr>
            <td className="col-2">부품명</td>
            <td className="col-2">교환주기(km)</td>
            <td className="col-2">교환주기(일)</td>
            <td className="col-2">교환주기(횟수)</td>
            <td className="col-3">메시지</td>
            <td className="col-1">적용여부</td>
          </tr>
          </thead>
          <tbody>
          {
          this.state.prcList && 
            this.state.prcList.map((item, index) => {
              return(
              <tr key={index}>
                <td>{item.grnm}</td>
                <td>
                <input type="input" className="form-control" name={"stdval"+index} defaultValue={item.stdval} onChange={(event)=>{this.onStdValChange(event)}}>
                </input>
                </td>
                <td>
                <input type="input" className="form-control" name={"stdval"+index} defaultValue={item.prdval} onChange={(event)=>{this.onPrdValChange(event)}}>
                </input>
                </td>
                <td>
                  <input type="input" className="form-control" name={"nmval"+index} defaultValue={item.nmval} onChange={(event)=>{this.onNmValChange(event)}}>
                  </input>
                </td>
                <td>
                  <input type="input" className="form-control" name={"msg"+index} defaultValue={item.msg} onChange={(event)=>{this.onMessageChange(event)}}>
                  </input>
                </td>
                <td><input type="checkbox" className="form-control" name={"checkbox"+index} defaultChecked={item.applicability} onChange={(event)=>{this.onApplicabilityChange(event)}}></input></td>
              </tr>
              );
            })}
          </tbody>
        </table>
        </div>
          <button className="btn btn04 btn-success" onClick={this.updatePrcList}>저장</button>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { user } = state.auth;
  return {
    user,
  };
}

export default connect(mapStateToProps)(Setting_PRC);