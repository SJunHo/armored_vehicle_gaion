import React, { Component } from "react";
import driver_cisService from "../../../services/analysis/driver_cis.service";
import { connect } from "react-redux";

class Driver_cis extends Component {
  constructor(props) {
    super(props);
    this.getDriverAttitdList = this.getDriverAttitdList.bind(this);
    this.onSensorChange = this.onSensorChange.bind(this);
    this.onStdValChange = this.onStdValChange.bind(this);
    this.onMessageChange = this.onMessageChange.bind(this);
    this.updateDriverAttitd = this.updateDriverAttitd.bind(this);
    this.getSnsrList = this.getSnsrList.bind(this);
    this.onUsedvcdChange = this.onUsedvcdChange.bind(this);

    const {user} = this.props; 
    this.state = {
      user : user,
      driverAttitdList : [],
      snsrList : [],
    };
  }

  componentDidMount(){
    this.getDriverAttitdList();
    this.getSnsrList();
  }

  getDriverAttitdList(){
    driver_cisService.getList()
    .then((response) => {
      this.setState({
        driverAttitdList: response.data,
      });
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  getSnsrList(){
    driver_cisService.getSnsrList()
    .then((response) => {
      this.setState({
        snsrList: response.data,
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
    let driverAttitdSet = this.state.driverAttitdList;
    driverAttitdSet[index].stdval = value;
    driverAttitdSet[index].mdfcdt = new Date();
    driverAttitdSet[index].mdfr = this.state.user.username;
    this.setState({
      driverAttitdList: driverAttitdSet
    });
  }

  onSensorChange(e){
    const {name, value} = e.target;
    let index = name.split('sensor')[1];
    let driverAttitdSet = this.state.driverAttitdList;
    driverAttitdSet[index].snsrid = value;
    driverAttitdSet[index].mdfcdt = new Date();
    driverAttitdSet[index].mdfr = this.state.user.username;
    this.setState({
      driverAttitdList: driverAttitdSet
    });
  }

  onMessageChange(e){
    const {name, value} = e.target;
    let index = name.split('msg')[1];
    let driverAttitdSet = this.state.driverAttitdList;
    driverAttitdSet[index].msg = value;
    driverAttitdSet[index].mdfcdt = new Date();
    driverAttitdSet[index].mdfr = this.state.user.username;
    this.setState({
      driverAttitdList: driverAttitdSet
    });
  }

  onUsedvcdChange(e){
    const {name, checked} = e.target;
    let index = name.split('checkbox')[1];
    let driverAttitdSet = this.state.driverAttitdList;
    let usedvcd;
    if(checked){
      usedvcd = 'Y';
    }else{
      usedvcd = 'N';
    }
    driverAttitdSet[index].usedvcd = usedvcd;
    driverAttitdSet[index].mdfcdt = new Date();
    driverAttitdSet[index].mdfr = this.state.user.username;
    for(var i = 0; i < driverAttitdSet.length; i++){
      if(driverAttitdSet[i].daid === driverAttitdSet[index].daid){
        driverAttitdSet[i].usedvcd = usedvcd;
        driverAttitdSet[i].mdfcdt = new Date();
        driverAttitdSet[i].mdfr = this.state.user.username;
        const r = document.getElementById("checkbox"+i);
        r.checked = checked;
      }
    }
    this.forceUpdate();
    this.setState({
      driverAttitdList: driverAttitdSet
    },()=>{});
  }

  updateDriverAttitd(){
    driver_cisService.updateList(this.state.driverAttitdList)
    .then((response) => {
      console.log(response);
      if(response.status === 201){
        alert("정상등록되었습니다");
      }
    })
    .catch((e) => {
      console.log(e);
      alert("등록에 실패했습니다");
    });
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
        운전자 교정정보 설정
        </header>
        <div className="table-div table03">
        <table>
          <thead>
          <tr>
            <td className="col-2">자세교정 이름</td>
            <td className="col-3">센서이름</td>
            <td className="col-2">기준값</td>
            <td className="col-4">메시지</td>
            <td className="col-1">적용여부</td>
          </tr>
          </thead>
          <tbody>
          {
          this.state.driverAttitdList && 
            this.state.driverAttitdList.map((item, index) => {
              return(
              <tr key={index}>
                <td>{item.danm}</td>
                <td>
                <div className="form-group m-0">
                <select name={"sensor"+index} onChange={(e)=>{this.onSensorChange(e)}}
                        value={item.snsrid}>
                {this.state.snsrList.map((option)=> (
                  <option key={option.snsrid}
                          value={option.snsrid}>
                          {option.snsrnm}
                          </option>
                ))} 
                </select>
                </div>
                  
                {/* <input type="input" className="form-control form-control-style" name={"max"+index} defaultValue={item.stdval} onChange={(event)=>{this.onMaxChange(event)}}></input> */}
                </td>
                <td><input type="input" className="form-control form-control-style" name={"stdval"+index} value={item.stdval} onChange={(event)=>{this.onStdValChange(event)}}></input></td>
                <td><input type="input" className="form-control form-control-style" name={"msg"+index} value={item.msg} onChange={(event)=>{this.onMessageChange(event)}}></input></td>
                <td><input type="checkbox" className="form-control form-control-style" name={"checkbox"+index} id={"checkbox"+index} checked={item.usedvcd === 'Y' ? true : false} onChange={(event)=>{this.onUsedvcdChange(event)}}></input></td>
              </tr>
              );
            })}
          </tbody>
        </table>
        </div>
          <button className="btn btn04 btn-success" onClick={this.updateDriverAttitd}>저장</button>
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

export default connect(mapStateToProps)(Driver_cis);