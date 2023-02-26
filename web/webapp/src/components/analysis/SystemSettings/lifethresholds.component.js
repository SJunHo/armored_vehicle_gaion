import React, { Component } from "react";
import { connect } from "react-redux";
import lifeThresholdService from "../../../services/analysis/lifethreshold.service";

class LifeThresholds extends Component {
  constructor(props) {
    super(props);
    this.retrieveThresholdList = this.retrieveThresholdList.bind(this);
    this.onDistanceChange = this.onDistanceChange.bind(this);
    this.onYearsChange = this.onYearsChange.bind(this);
    this.onUsedvcdChange = this.onUsedvcdChange.bind(this);
    this.updateThreshold = this.updateThreshold.bind(this);

    const {user} = this.props; 
    this.state = {
      user : user,
      thresholdList : [],
    };
  }

  componentDidMount(){
    this.retrieveThresholdList();
  }

  retrieveThresholdList(){
    lifeThresholdService.getThresholdList()
    .then((response) => {
      this.setState({
        thresholdList: response.data,
      });
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }
  
  onDistanceChange(e){
    const {name, value} = e.target;
    let index = name.split('distance')[1];
    let thresholdSet = this.state.thresholdList;
    thresholdSet[index].distance = value;
    thresholdSet[index].mdfcdt = new Date();
    thresholdSet[index].mdfr = this.state.user.username;
    this.setState({
      thresholdList: thresholdSet
    });
  }

  onYearsChange(e){
    const {name, value} = e.target;
    let index = name.split('years')[1];
    let thresholdSet = this.state.thresholdList;
    thresholdSet[index].years = value;
    thresholdSet[index].mdfcdt = new Date();
    thresholdSet[index].mdfr = this.state.user.username;
    this.setState({
      thresholdList: thresholdSet
    });
  }

  onUsedvcdChange(e){
    const {name, checked} = e.target;
    console.log(checked);
    let index = name.split('checkbox')[1];
    let usedvcd;
    if(checked){
      usedvcd = 'Y';
    }else{
      usedvcd = 'N';
    }
    let thresholdSet = this.state.thresholdList;
    thresholdSet[index].usedvcd = usedvcd;
    thresholdSet[index].mdfcdt = new Date();
    thresholdSet[index].mdfr = this.state.user.username;
    this.setState({
      thresholdList: thresholdSet
    });
  }

  updateThreshold(){
    lifeThresholdService.updateList(this.state.thresholdList)
    .then((response) => {
      console.log(response);
      alert("정상등록되었습니다");
    })
    .catch((e) => {
      alert("등록에 실패했습니다");
    });
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
        잔존수명 기준값 설정
        </header>
        <div className="table-div table03">
        <table>
          <thead>
          <tr>
            <td className="col-3">센서명</td>
            <td className="col-3">기준수명(KM)</td>
            <td className="col-3">기준수명(년)</td>
            <td className="col-3">적용여부</td>
          </tr>
          </thead>
          <tbody>
          {
          this.state.thresholdList && 
            this.state.thresholdList.map((item, index) => {
              return(
              <tr key={item.snsrtype}>
                <td>{item.snsrtype}</td>
                <td><input type="input" className="form-control form-control-style" name={"distance"+index} defaultValue={item.distance} onChange={(event)=>{this.onDistanceChange(event)}}></input></td>
                <td><input type="input" className="form-control form-control-style" name={"years"+index} defaultValue={item.years} onChange={(event)=>{this.onYearsChange(event)}}></input></td>
                <td><input type="checkbox" className="form-control form-control-style" name={"checkbox"+index} checked={item.usedvcd === 'Y' ? true:false} onChange={(event)=>{this.onUsedvcdChange(event)}}></input></td>
              </tr>
              );
            })}
          </tbody>
        </table>
        </div>
          <button className="btn btn04 btn-success" onClick={this.updateThreshold}>저장</button>
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

export default connect(mapStateToProps)(LifeThresholds);