import React, { Component } from "react";
import { connect } from "react-redux";
import settingthresholdService from "../../../services/analysis/settingthreshold.service";

class SettingThresholds extends Component {
  constructor(props) {
    super(props);
    this.retrieveThresholdList = this.retrieveThresholdList.bind(this);
    this.onMaxChange = this.onMaxChange.bind(this);
    this.onMinChange = this.onMinChange.bind(this);
    this.onApplicabilityChange = this.onApplicabilityChange.bind(this);
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
    settingthresholdService.getThresholdList()
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
  
  onMaxChange(e){
    const {name, value} = e.target;
    let index = name.split('max')[1];
    let thresholdSet = this.state.thresholdList;
    thresholdSet[index].max = value;
    thresholdSet[index].mdfcdt = new Date();
    thresholdSet[index].mdfr = this.state.user.username;
    this.setState({
      thresholdList: thresholdSet
    });
  }

  onMinChange(e){
    const {name, value} = e.target;
    let index = name.split('min')[1];
    let thresholdSet = this.state.thresholdList;
    thresholdSet[index].min = value;
    thresholdSet[index].mdfcdt = new Date();
    thresholdSet[index].mdfr = this.state.user.username;
    this.setState({
      thresholdList: thresholdSet
    });
  }

  onApplicabilityChange(e){
    const {name, checked} = e.target;
    let index = name.split('checkbox')[1];
    let thresholdSet = this.state.thresholdList;
    thresholdSet[index].applicability = checked;
    thresholdSet[index].mdfcdt = new Date();
    thresholdSet[index].mdfr = this.state.user.username;
    this.setState({
      thresholdList: thresholdSet
    });
  }

  updateThreshold(){
    settingthresholdService.updateList(this.state.thresholdList)
    .then((response) => {
      console.log(response);
      alert(response.status);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
        임계값 설정
        </header>
        <table>
          <thead>
          <tr>
            <td>센서명</td>
            <td>최대임계치</td>
            <td>최소임계치</td>
            <td>적용여부</td>
          </tr>
          </thead>
          <tbody>
          {
          this.state.thresholdList && 
            this.state.thresholdList.map((item, index) => {
              return(
              <tr key={item.snsrid}>
                <td>{item.expln}</td>
                <td><input type="input" name={"max"+index} defaultValue={item.max} onChange={(event)=>{this.onMaxChange(event)}}></input></td>
                <td><input type="input" name={"min"+index} defaultValue={item.min} onChange={(event)=>{this.onMinChange(event)}}></input></td>
                <td><input type="checkbox" name={"checkbox"+index} defaultChecked={item.applicability} onChange={(event)=>{this.onApplicabilityChange(event)}}></input></td>
              </tr>
              );
            })}
          </tbody>
        </table>
          <button className="submitButton" onClick={this.updateThreshold}>적용</button>
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

export default connect(mapStateToProps)(SettingThresholds);