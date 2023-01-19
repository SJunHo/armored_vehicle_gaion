import React, { Component } from "react";
import { connect } from "react-redux";
import TreeMenu, { ItemComponent } from "react-simple-tree-menu";
import '../../../../node_modules/react-simple-tree-menu/dist/main.css';
import { Bar } from "@nivo/bar";
import DatePicker from "react-datepicker";
import statisticalService from "../../../services/analysis/statistical.service";
import "../../../../node_modules/react-datepicker/dist/react-datepicker.css";
import {ko} from "date-fns/locale";
import Table from "./statisticalTable.component";
import amvhimg from "../../../amvhimg.png";

import 'react-notifications/lib/notifications.css';
import {NotificationContainer, NotificationManager} from 'react-notifications';

import '../../../css/fonts.css';
import '../../../css/style.css';
import {FaBell} from "react-icons/fa";
import moment from 'moment';

class Statistical extends Component {
  
  constructor(props) {
    super(props);
    this.clickOutlierWaning = this.clickOutlierWaning.bind(this);
    this.clickTroubleShooting = this.clickTroubleShooting.bind(this);
    this.clickVehicleInfo = this.clickVehicleInfo.bind(this);
    this.togglePopup = this.togglePopup.bind(this);
    const {user} = this.props; 
    this.state = {
      user : user,
      content: "",
      loading: false,
      treeArray: "",
      graphData: "",
      tableData: "",
      tableResult: [],
      amvhTable: new Map(),
      avgsdtGraphData:[],
      engnnldnrateGraphData:[],
      mvmtdstcGraphData:[],
      mvmttimeGraphData:[],
      startDate : new Date(),
      date : "",
      graphLevel : "0",
      graphUrl : "1",
      popUpList : [],
      popUpState : false,
      columns : [
        {
          Header: '구분',
          columns: [
              {
                  Header : ' ',
                  accessor: 'bn',
              },
          ]
        },
        {
          Header: '총대수',
          columns: [
              {
                  Header : '  ',
                  accessor: 'allcount',
              },
          ]
        },
        {
          Header: '운행여부',
          columns: [
            {
              Header: '미운행',
              accessor: 'ndrive',
            },
            {
              Header: '운행',
              accessor: 'drive',
            },
          ],
        },
        {
          Header: '상태진단',
          columns: [
            {
              Header: '정상',
              accessor: 'normal',
            },
            {
              Header: '이상치 경고',
              accessor: 'outlier',
            },
            {
              Header: '고장 경고',
              accessor: 'broken',
            },
          ],
        },
      ]
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.createNotification = this.createNotification.bind(this);
    this.popUpClear = this.popUpClear.bind(this);

  }

  componentDidMount() {

    this.setState({
      loading : true
    });
    statisticalService.getTree().then(
      response => {
        // let treedata = response.data;
        // let divdepth = new Array();
        // divdepth.push("tree");
        // treedata.tree.nodes.forEach((i)=>{
        //   let treeid = "tree/" +i.key;
        //   divdepth.push(treeid);
        // })
        this.setState({
          treeArray: response.data,
        });
        console.log(response.data);
      },
      error => {
        this.setState({
          treeArray:
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        });
      }
    );
    let param = {
      level : 0,
      url : 1,
      date : this.state.startDate
    }

    statisticalService.getGraph(param).then(
      response => {
        this.setState({
          graphData : response.data,
        });
      },
      error => {
        this.setState({
          graphData:
          (error.response && error.response.data) ||
          error.message ||
          error.toString()
        });
      }
    );

    let startTable = new Date();
    statisticalService.getTable(param).then(
      response => {
        this.setState({
          tableData : response.data,
        });
        let endTable = new Date();
        console.log(endTable - startTable);
      },
      error => {
        this.setState({
          tableData:
          (error.response && error.response.data) ||
          error.message ||
          error.toString()
        });
      }
    );

    statisticalService.getPopUpInfo(this.state.user.id).then(
      response => {
        console.log(response.data);
        let data = [];
        data = response.data;
        if(data.length !== 0){
          this.setState({
            popUpList : response.data
          });
        }
      },
      error => {
        console.log(error);
      }
    ,()=>{});

      setInterval(()=>{
        if(NotificationManager.listNotify.length === 0 && this.state.popUpState){
          this.setState({
            popUpState : false,
          });
        }
      },1000);
  }

  componentDidUpdate(prevProps, prevState){
    if(prevState.graphData !== this.state.graphData){
      this.getPutGraphData(this.state.graphData);
    }
    if(prevState.startDate !== this.state.startDate){
      this.getGraphData(this.state.graphLevel,this.state.graphUrl,this.state.startDate);
      this.getTableData(this.state.graphLevel,this.state.graphUrl,this.state.startDate);
    }
    if(prevState.tableData !== this.state.tableData){
      this.getPutTableData(this.state.tableData);
    }
    if(prevState.amvhTable !== this.state.amvhTable){
      this.getPutTableData(this.state.tableData);
    }
    if(prevState.popUpList !== this.state.popUpList){
      this.createNotification();
    }

  }

  getGraphData(level,url,date){
    let param = {
      level : level,
      url : url,
      date : date
    }
    statisticalService.getGraph(param).then(
      response => {
        this.setState({
          graphData: response.data
        });
      },
      error => {
        this.setState({
          graphData:
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  getTableData(level,url,date){

    this.setState({
      loading:true,
    });

    let param = {
      level : level,
      url : url,
      date : date
    }

    statisticalService.getTable(param).then(
      response => {
        this.setState({
          tableData: response.data
        });
      },
      error => {
        this.setState({
          tableData:
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  onClickTree(param){
    this.setState({
      graphLevel : param.level,
      graphUrl : param.url
    });
    if(param.sda==null){
      this.getTableData(param.level,param.url,this.state.startDate);
      this.getGraphData(param.level,param.url,this.state.startDate);
    }
    if(param.level >= 3){
      this.clickVehicleInfo(param.label);
    }
  }
  
  getPutGraphData(param){
    let output = Object.values(param);
    if(output.length > 0){
      let avgsdtData;
      let engnnldnrateData;
      let mvmtdstcData;
      let mvmttimeData;
      Object.entries(Object.values(output[0])).forEach(e => {

        switch(e[0]){
          case '0':
            avgsdtData = e[1];
            break;
          case '1':
            engnnldnrateData = e[1];
            break;
          case '2':
            mvmtdstcData = e[1];
            break;
          case '3':
            mvmttimeData = e[1];
            break;
          default:
            break;
        }
      });

      let avgsdt = [];
      if(avgsdtData!= null){
        Object.entries(avgsdtData).forEach(two => {
            let avgs = {
            bn : two[0],
            value : Math.round(two[1]*1000) / 1000
          }
          avgsdt.push(avgs);
        });
      }
      console.log(avgsdt);
      let engnnldnrate = [];
      if(engnnldnrateData!= null){
        Object.entries(engnnldnrateData).forEach(two => {
            let engnnldn = {
            bn : two[0],
            value : Math.round(two[1]*1000) / 1000
          }
          engnnldnrate.push(engnnldn);
        });
      }

      let mvmtdstc = [];
      if(mvmtdstcData!= null){
        Object.entries(mvmtdstcData).forEach(two => {
            let mvmtd = {
            bn : two[0],
            value : Math.round(two[1]*1000) / 1000
          }
          mvmtdstc.push(mvmtd);
        });
      }

      let mvmttime = [];
      if(mvmttimeData!= null){
        Object.entries(mvmttimeData).forEach(two => {
            let mvmtt = {
            bn : two[0],
            value : Math.round(two[1]*1000) / 1000
          }
          mvmttime.push(mvmtt);
        });
      }
      this.setState({
        avgsdtGraphData : avgsdt,
        engnnldnrateGraphData : engnnldnrate,
        mvmtdstcGraphData : mvmtdstc,
        mvmttimeGraphData : mvmttime,
      });
    }
  }
  
  getPutTableData(param){
    let table = Object.values(param);
    let tabledata = [];
    if(this.state.graphLevel === 2){
      Object.entries(table[0]).forEach(tab=>{
        tabledata = tab[1];
      });
      let tableResultArray = [];
      tableResultArray.push(tabledata[0]);

      this.setState({
        tableResult : tableResultArray,
        amvhTable : tabledata[1]
      },()=>{
      });
      
    }else{
      Object.entries(table[0]).forEach(tab=>{
        tabledata = tab[1];
      });
      this.setState({
        tableResult : tabledata,
        amvhTable : ""
      });
    }

    this.setState({
      loading : false,
    },()=>{
      console.log(this.state.tableResult);
    });

  }

  handleChange(date){
    this.setState({
      startDate : date
    })
  }

  handleSubmit(e){
    e.preventDefault();
    let main = this.state.startDate
  }

  clickOutlierWaning(param){
    statisticalService.getId(param).then((response) => {
      if(window.confirm("이상치경고모니터링으로 이동하시겠습니까?")){
        window.location.href = "/monitoroutlierwarning/"+response.data;
      }
    })
    .catch((e) => {
        console.log(e);
    }); 
    
  }

  clickTroubleShooting(param){
    statisticalService.getId(param).then((response) => {
      if(window.confirm("고장진단경고모니터링으로 이동하시겠습니까?")){
        window.location.href = "/monitordiagnostictroublealerts/"+response.data;
      }
    })
    .catch((e) => {
        console.log(e);
    }); 
    
  }

  clickVehicleInfo(param){
    statisticalService.getId(param).then((response) => {
      if(window.confirm("차량정보조회화면으로 이동하시겠습니까?")){
        window.location.href = "/searchEachInfo/"+response.data;
      }
    })
    .catch((e) => {
        console.log(e);
    }); 
  }

  togglePopup(){
    this.setState({
      showPopup : !this.state.showPopup
    })
  }

  createNotification(){
    this.setState({
      popUpState : true
    });
    if(this.state.popUpList.length > 0){
      this.state.popUpList.forEach(v => {
        if(v.includes("경고")){
          NotificationManager.error(v, 'Notice', 40000);
        }else{
          NotificationManager.warning(v, 'Notice', 40000);
        }
      });      
    }
  }

  popUpClear(){
    this.setState({
      popUpState : false
    });
    NotificationManager.removeAll();
  }

  render() {
    
    return (
      <div className="container">
        <div className="sub_title">
              <h1 style={{display : "inline-block"}}>차륜형 장갑차 센서데이터 수집, 분석 체계</h1>
              { this.state.popUpList &&
                <button style={{display : this.state.popUpState ? "none" : "inline", float:"right"}} onClick={this.createNotification} className="btn btn-alarm"><FaBell/></button>
              }
        </div>
        <div className="row min" disabled={this.state.loading}>
          {this.state.loading && (   
            <div className="d-flex justify-content-center loading-box">
                  <div className="spinner-border loading-in" role="status">
                      <span className="sr-only">Loading...</span>
                  </div>
              </div>
          )}

          <div className="Tree col-2">
          <TreeMenu data={this.state.treeArray}
              initialOpenNodes={['tree','tree/2','tree/3','tree/4', 'tree/16', 'tree/20']}
              >
            {({ items }) => (
              <div>
                <ul className="tree-item-group">
                {console.log(items)}
                {items.map(({key, ...props }) => (
                    <ul key={key} className={
                      props.label.includes("25사단") 
                          ? (props.isOpen ? 'Opened index1' : 'Closed index1') 
                          : props.label.includes("37사단") 
                                ? (props.isOpen ? 'Opened index2' : 'Closed index2') 
                                : props.label.includes("군수교") 
                                ? (props.isOpen ? 'Opened index3' : 'Closed index3') 
                                    : props.label.includes("수도방위사령부") 
                                    ? (props.isOpen ? 'Opened index4' : 'Closed index4')
                                        : props.label.includes("35사단") 
                                        ? (props.isOpen ? 'Opened index5' : 'Closed index5')  
                                        : (props.isOpen ? 'Opened' : 'Closed') 
                                        }>

                      <ItemComponent key={key} {...props} onClick={()=>{this.onClickTree(props);}}/>
                    </ul>
                  ))}
                </ul>
              </div>
            )}
          </TreeMenu>
        </div>
        <div className="contents col">
          <div className="stable">
            <div className="detepicker-div">
            {/* <button className="btn-exceldownload" onClick={() => this.handleExcelDownload()}>다운로드</button> */}
            <form className="datepicker-form" onSubmit={this.onFormSubmit}>
              <div className="form-group sub-date">
                <DatePicker 
                  selected={this.state.startDate}
                  onChange={this.handleChange}
                  name="startDate"
                  dateFormat="yyyy/MM/dd"
                  locale={ko}
                />
              </div>
            </form>
            </div>
            <div className="table-div">
            {
              (
                this.state.tableResult &&
                <Table columns={this.state.columns} data={this.state.tableResult}/> 
                )
            }

            {
              this.state.loading ? "" :
              (this.state.amvhTable &&
              Object.entries(this.state.amvhTable).map(amvh => {
                  return <div className="amvh-div" key={amvh[0]}>
                            <div className="amvh-img">
                            <img src={amvhimg} alt="amvh"></img>
                            <p>{amvh[0]}</p>
                            </div>
                            <div className="amvh-button">
                            {
                              amvh[1].includes("NN")
                              ? <button className="btn btn-light" onClick={()=>{this.clickVehicleInfo(amvh[0]);}}>정상</button>
                              : <button className="btn btn-light" disabled>정상</button>
                            }

                            {
                              amvh[1].includes("O")
                              ? <button className="btn btn-danger" onClick={()=>{this.clickOutlierWaning(amvh[0]);}}>이상</button>
                              : <button className="btn btn-light" disabled>이상</button> 
                            }

                            {
                              amvh[1].includes("B")
                              ? <button className="btn btn-danger" onClick={()=>{this.clickTroubleShooting(amvh[0]);}}>고장</button>
                              : <button className="btn btn-light" disabled>고장</button>
                            }
                            </div>
                          </div>
              })
              )
            }
            </div>
          </div>
          <div className="sgraph">
                  <div className="graph-box">
                    <p>평균운행거리(km)</p>
                    <Bar
                      width={230}
                      height={200}
                      margin={{ top: 30, right: 5, bottom: 30, left: 10 }}
                      data={this.state.avgsdtGraphData}
                      indexBy="bn"
                      keys={["value"]}
                      // colors={color}
                      enableGridX={true}
                      enableLabel={false}
                      />
                  </div>
                  <div className="graph-box">
                    <p>평균운행시간(h)</p>
                    <Bar
                      width={230}
                      height={200}
                      margin={{ top: 30, right: 5, bottom: 30, left: 5 }}
                      data={this.state.engnnldnrateGraphData}
                      indexBy="bn"
                      keys={["value"]}
                      // colors={color}
                      labelSkipWidth={5}
                      labelSkipHeight={5}
                      enableGridX={true}
                      enableLabel={false}
                    />
                  </div>
                  <div className="graph-box">
                    <p>평균속력(km/h)</p>
                    <Bar
                      width={230}
                      height={200}
                      margin={{ top: 30, right: 5, bottom: 30, left: 5 }}
                      data={this.state.mvmtdstcGraphData}
                      indexBy="bn"
                      keys={["value"]}
                      //colors={color}
                      enableGridX={true}
                      enableLabel={false}
                    />
                  </div>
                  <div className="graph-box">
                    <p>엔진 공회전 비율(%)</p>
                    <Bar
                      width={230}
                      height={200}
                      margin={{ top: 30, right: 10, bottom: 30, left: 5 }}
                      data={this.state.mvmttimeGraphData}
                      indexBy="bn"
                      keys={["value"]}
                      //colors={color}
                      enableGridX={true}
                      enableLabel={false}
                    />
                  </div>
                
          </div> 
        </div>
        { this.state.popUpList &&
          (<div className={`sidebar-menu${this.state.popUpState === true ? '-open' : '-none'}`}>
            <button className="button small float-left" style={{display : !this.state.popUpState ? "none" : "inline"}} onClick={this.popUpClear}>X</button>
          <NotificationContainer/>     
          </div>) 
        }     
      </div>  
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

export default connect(mapStateToProps)(Statistical);