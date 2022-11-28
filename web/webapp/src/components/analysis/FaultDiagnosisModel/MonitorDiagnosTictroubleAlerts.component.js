import React, { Component } from "react";
import ReactDOM from 'react-dom';

import {ko} from "date-fns/locale";
import DatePicker from "react-datepicker";
import "../../../css/statusdatalookup.css"
import monitorDiagnostDataService from "../../../services/analysis/monitordiagnost.service";
import Pagination from "@material-ui/lab/Pagination";
import TableByTrouble from "./tableByTrouble.component";
import moment from "moment/moment";
import Chart from 'chart.js/auto';
import "./tableByTrouble.css";

export default class MonitorDiagnosTictroubleAlerts extends Component {
  constructor(props) {
    super(props);
    this.lineChart1Ref = React.createRef();
    this.lineChart2Ref = React.createRef();

    this.state = {
      startDate : new Date(),
      endDate : new Date(),
      selectedVehicle : "",
      selectedPart: "BEARING",
      allVehicleInfo: [],
      
      page: 1,
      count: 0,
      pageSize: 10,

      troubleList: [],
      berList: [],
      searchPaginationBtnClick: false,
      warnMsg : false,
      existChart: false,
      chartLoading : false,
      tableLoading : false,
      paramdescList : [],
      clickOn : false,
    };

    this.handleSubmit = this.handleSubmit.bind(this);

    this.getAllVehicleInfo = this.getAllVehicleInfo.bind(this);
    this.vehicleIdChange = this.vehicleIdChange.bind(this);
    this.vehiclePartChange = this.vehiclePartChange.bind(this);
    this.startDateChange = this.startDateChange.bind(this);
    this.endDateChange = this.endDateChange.bind(this);
    this.searchTroubleThings = this.searchTroubleThings.bind(this);

    this.handlePageChange = this.handlePageChange.bind(this);

    this.downloadExcel = this.downloadExcel.bind(this);
    this.chartMaker = this.chartMaker.bind(this);

    this.findChartIdx = this.findChartIdx.bind(this);
    this.createTable = this.createTable.bind(this);
    this.createAnalysisTable = this.createAnalysisTable.bind(this);
    this.chartLoad = this.chartLoad.bind(this);
  }

  componentDidMount(){
    console.log(this.props.match.params.id);
    this.getAllVehicleInfo();
    
  }

  componentDidUpdate(PrevState, PrevProps){

  }

  getAllVehicleInfo(){    //모든차량 정보가져오는 함수  (모든 호기 호출)
    monitorDiagnostDataService.getAllVehicleInfo()
    .then((response) => {
      let param = "";
      if(this.props.match.params.id === undefined){
        param = response.data[0].sdaid;
      }else{
        param = this.props.match.params.id;
        this.props.match.params.id = undefined;
      }
      
      this.setState({
        allVehicleInfo: [...response.data],
        selectedVehicle: param,
      }, () => {
        
        // this.searchTroubleThings();   //초기 데이터세팅을 위해
      })
    })
    .catch((e) => {
      console.log(e);
    })
  }

  handleSubmit(event) {
    event.preventDefault();
  }

  vehicleIdChange(res){
    this.setState({
      selectedVehicle : res.target.value
    });
  }
  vehiclePartChange(res){
    this.setState({
      selectedPart: res.target.value,
    });
  }
  startDateChange(res){
    this.setState({
      startDate : res
    });
  }
  endDateChange(res){
    this.setState({
      endDate: res
    });
  }

  searchTroubleThings(source) {   //조회하기 버튼으로 데이터 조회
    const { selectedVehicle, selectedPart, startDate, endDate, page, pageSize } = this.state;
    console.log(source);
    const analysisElement = document.getElementById('analysisTable');
    this.setState({
      tableLoading : true,
      clickOn : false,
      paramdescList : [],
    }, () =>{
      ReactDOM.unmountComponentAtNode(analysisElement);
    });

    if(this.state.existChart){
      
      this.lineChart1.destroy();
      this.lineChart2.destroy();

      this.setState({
        existChart: false,
        warnMsg : false,
      });
    }
    let data;
    if(source === "Pagination"){      //페이징된 버튼 < 1 2 3 ~ >눌렀을때  
      data = {
        sdaid : selectedVehicle,
        part : selectedPart,
        startDate : startDate,
        endDate : endDate,
        page: page,
        size: pageSize,
      };
      this.createTable(data);
    }else{            //페이징 아닌, 조회하기버튼 클릭으로, 페이지를 this.state.page, size를 초기화함
      data = {
        sdaid : selectedVehicle,
        part : selectedPart,
        startDate : startDate,
        endDate : endDate,
        page: 1,
        size: 10,
      };
      this.setState({
        page: 1,
        size: 10,
      }, () => {
        this.createTable(data);
      });
    }
  }

  createTable(data){    //테이블 컴포넌트 호출하는 함수
    console.log(this.state.existChart);
    
    const parentElement = document.getElementById('includeTroubleTable');
    const { selectedVehicle } = this.state;


    switch(this.state.selectedPart){
      case 'BEARING':
        monitorDiagnostDataService.searchTroubleBer(data)
        .then((response) => {
          console.log(response.data);
          const { troubleList, paging} = response.data;
          this.setState({
            troubleList: troubleList,
            count: paging.totalPageCount,
            tableLoading : false,
          }, () => {

            ReactDOM.render(
            <TableByTrouble data={this.state.troubleList} infoData={data} func={this.createAnalysisTable} sdaid={selectedVehicle} 
            load={this.chartLoad}
            />, 
            parentElement);

            let warnMsg = false;
            this.state.troubleList.forEach((el) => {
              if(el.ai_LBPFO === "1"){
                warnMsg = true;
              }else if(el.ai_LBPFI === "1"){
                warnMsg = true;
              }else if(el.ai_LBSF === "1"){
                warnMsg = true;
              }else if(el.ai_LFTF === "1"){
                warnMsg = true;
              }else if(el.ai_RBPFO === "1"){
                warnMsg = true;
              }else if(el.ai_RBPFI === "1"){
                warnMsg = true;
              }else if(el.ai_RBSF === "1"){
                warnMsg = true;
              }else if(el.ai_RFTF === "1"){
                warnMsg = true;
              }
            })
            if(warnMsg){
              alert("고장경고");
            }
          });
        })
        .catch((e) => {
          console.log(e);
        })
        break;

      case 'ENGINE':
        monitorDiagnostDataService.searchTroubleEng(data)
        .then((response) => {
          console.log(response.data);
          const { troubleList, paging} = response.data;
          this.setState({
            troubleList: troubleList,
            count: paging.totalPageCount,
            tableLoading : false,
          }, () => {

            ReactDOM.render(
            <TableByTrouble data={this.state.troubleList} infoData={data} func={this.createAnalysisTable} sdaid={selectedVehicle} 
            />
            , parentElement);
            let warnMsg = false;
            this.state.troubleList.forEach((el) => {
              if(el.ai_ENGINE === "1"){
                warnMsg = true;
              }
            })
            if(warnMsg){
              alert("고장경고");
            }

          });
        })
        .catch((e) => {
          console.log(e);
        })
        break;

      case 'GEARBOX':
        monitorDiagnostDataService.searchTroubleGrb(data)
        .then((response) => {
          console.log(response.data);
          const { troubleList, paging} = response.data;
          this.setState({
            troubleList: troubleList,
            count: paging.totalPageCount,
            tableLoading : false,
          }, () => {

            ReactDOM.render(
            <TableByTrouble data={this.state.troubleList} infoData={data} func={this.createAnalysisTable} sdaid={selectedVehicle}
             />
            , parentElement);
            let warnMsg = false;
            this.state.troubleList.forEach((el) => {
              if(el.ai_GEAR === "1"){
                warnMsg = true;
              }
            })
            if(warnMsg){
              alert("고장경고");
            }
          });
        })
        .catch((e) => {
          console.log(e);
        })
        break;

      case 'WHEEL':
        monitorDiagnostDataService.searchTroubleWhl(data)
        .then((response) => {
          console.log(response.data);
          const { troubleList, paging} = response.data;
          this.setState({
            troubleList: troubleList,
            count: paging.totalPageCount,
            tableLoading : false,
          }, () => {

            ReactDOM.render(
            <TableByTrouble data={this.state.troubleList} infoData={data} func={this.createAnalysisTable} sdaid={selectedVehicle} 
            />
            , parentElement);
            let warnMsg = false;
            this.state.troubleList.forEach((el) => {
              if(el.ai_LW === "1"){
                warnMsg = true;
              }else if(el.ai_RW === "1"){
                warnMsg = true;
              }
            })
            if(warnMsg){
              alert("고장경고");
            }
          });
        })
        .catch((e) => {
          console.log(e);
        })
    }
  }

  createAnalysisTable(res, list){
    this.setState({
      clickOn : true,
    })
    const analysisElement = document.getElementById('analysisTable');
    let data = {
      paramList : list,
      detpartid : this.state.selectedPart,
      
    };
    
    if(res === null && list === null){
      this.setState({
        clickOn : false,
        paramdescList : [],
      }, () =>{
        ReactDOM.unmountComponentAtNode(analysisElement);
      });
    }else{
      monitorDiagnostDataService.searchParamdesc(data)
      .then((response) => {
        console.log(response.data);
        this.setState({
          paramdescList : response.data,
            }, () => {
              
              ReactDOM.render(
                <div className="contain">
                  <header className="jumbotron">
                  고장 원인 분석
                  </header>
                  <div className="contents08">
                    <table className="table04" style={{whiteSpace:"pre-line"}}>
                        <thead>
                          <tr>
                            <td>파라미터</td>
                            <td>파라미터 설명</td>
                            <td>고장 원인</td>
                          </tr>
                        </thead>
                        <tbody>
                          {
                            this.state.paramdescList &&
                            this.state.paramdescList.map((element,index) => {
                              return (
                                <tr key={index}>
                                  <td>{element.param}</td>
                                  <td>{element.desc}</td>
                                  <td style={{textAlign:'left'}}>{element.faildesc}</td>
                                </tr>
                              );
                            })
                          }
                        </tbody>
                      </table>
                    </div>
                  </div>
                , analysisElement);
            });
      })
      .catch((e) => {
        console.log(e);
      })
    }
  }

  handlePageChange(event, value) {    //페이지교체함수
    this.setState({
      page: value,
    }, () => {
      this.searchTroubleThings("Pagination");
    })
  }
  downloadExcel() {   //엑셀다운로드 함수 
    const { selectedVehicle, selectedPart, startDate, endDate } = this.state;
    
    let data = {
      sdaid : selectedVehicle,
      part : selectedPart,
      startDate : startDate,
      endDate : endDate,
    }
    monitorDiagnostDataService.downloadExcel(data)
    .then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'troubleData.xlsx');
      document.body.appendChild(link);
      link.click();
    })
    .catch((e) => {
      console.log(e);
    })
  }

  chartMaker(res, date) {     //차트만드는 함수
    console.log("차트생성");
    this.setState({
      clickOn : true,
    });
    this.setState({
      chartLoading : true
    });

    if(res === null){
      this.lineChart1.destroy();
      this.lineChart2.destroy();
      this.setState({
        existChart: false,
        chartLoading : false,
      },() => {console.log(this.state.chartLoading);});
      console.log("null");
    }else{
      console.log("생성");
      this.setState({
        existChart: true,
        chartLoading : true,
      },() => {console.log("True : " + this.state.chartLoading);});
      let dateForChart = [];  //x축 라벨
      let timeForChart = [];  //임시데이터
      let wrpmForChart = [];  //임시데이터

      res.data.forEach((el, idx) => {
        dateForChart.push(moment(el.date).format('hh:mm:ss'));
        timeForChart.push(el.time);
        wrpmForChart.push(el.w_RPM);
      })
      let sendData = [];
      let clickIdx = 0;
      let minIdx = 0;
      let maxIdx = 0;
      
      sendData = this.findChartIdx(dateForChart, moment(date).format('hh:mm:ss'));  
      clickIdx = sendData[0];
      minIdx = sendData[1];
      maxIdx = sendData[2];
      dateForChart = sendData[3];

      if(this.state.existChart){
        this.lineChart1.destroy();
        this.lineChart2.destroy();
      }
      
      const lineChart1El = this.lineChart1Ref.current.getContext('2d');
      this.lineChart1 = new Chart(lineChart1El, {
        type: 'line',
        data: {
          labels: [],
          datasets: [{          
            borderWidth: 1,
            radius: 0,
            borderColor: 'white',
            pointRadius: 1,
            label: "time",
            data: [],
          }],
        },
        options: {
          animation: {
            duration: 0
          },
          responsive: false,
          title:{
            fontSize:12,
            fontColor:"#ffffff",
          },
          scales: {
            y:{
              ticks: {color: "white", beginAtZero: true}
            },
            x: {
              ticks: {color: "white", beginAtZero: true}
            }
          }
        }
      });

      const lineChart2El = this.lineChart2Ref.current.getContext('2d');
      this.lineChart2 = new Chart(lineChart2El, {
        type: 'line',
        data: {
          labels: [],
          datasets: [{
            borderWidth: 1,
            radius: 0,
            borderColor: 'white',
            pointRadius: 1,
            label: "w_RPM", 
            data: [],
          }],
        },
        options: {
          animation: {
            duration: 0
          },
          responsive: false,
          title:{
            fontSize:12,
            fontColor:"#000000",
          },
          scales: {
            y:{
              ticks: {color: "white", beginAtZero: true}
            },
            x: {
              ticks: {color: "white", beginAtZero: true}
            }
          }
        }
      });

      dateForChart.forEach((el, idx) => {
        let chartVal1 = timeForChart[minIdx];
        let chartVal2 = wrpmForChart[minIdx];

        this.lineChart1.data.labels.push(dateForChart[idx]);
        this.lineChart1.data.datasets[0].data.push(chartVal1);

        this.lineChart2.data.labels.push(dateForChart[idx]);
        this.lineChart2.data.datasets[0].data.push(chartVal2);

        minIdx = minIdx + 1;
      })
      this.lineChart1.update();
      this.lineChart2.update();

      this.setState({
        existChart: true,
        chartLoading : false,
      },() => {console.log("FALSE : " +this.state.chartLoading);})
    }
  }

  findChartIdx(dateForChart, date){    //그래프의 범위를 설정하는 함수 클릭한 인덱스값 이전 100 / 이후 100
    let returnData = [];

    let clickRowIdx = 0;
    dateForChart.forEach((el, idx) => {
      if(date.includes(el)){
        clickRowIdx = idx;
      }
    });
    returnData.push(clickRowIdx);

    if(clickRowIdx < 99){   //클릭한 row의 이전값이  100보다 작을때, (그전값모두 포함)

      if(dateForChart[clickRowIdx+100] === undefined){    //클릭한 row의 이후 값이 100보다 작을때,
        dateForChart = dateForChart.slice(0, dateForChart.length);
        returnData.push(0);
        returnData.push(dateForChart.length);
        returnData.push(dateForChart);
        return returnData;

      }else{    //클릭한 row의 이후 값이 100보다 클때, 
        dateForChart = dateForChart.slice(0, clickRowIdx+100);
        returnData.push(0);
        returnData.push(clickRowIdx + 100);
        returnData.push(dateForChart);
        return returnData;
      }

    }else{    //클릭한 row의 이전 값이 100 보다 클때,
      let minIdx = 0;
      if(clickRowIdx === 99){
        returnData.push(0);
        minIdx = 0;
      } else{
        returnData.push(clickRowIdx - 100);
        minIdx = clickRowIdx - 100;
      }
      if(dateForChart[clickRowIdx+100] === undefined){     //클릭한 row의 이후 값이 100보다 작을때,
        returnData.push(dateForChart.length);
        dateForChart = dateForChart.slice(minIdx, dateForChart.length);
        returnData.push(dateForChart);
        return returnData;

      }else{    //클릭한 row의 이후 값이 100보다 클때, 
        dateForChart = dateForChart.slice(minIdx, clickRowIdx+100);
        returnData.push(clickRowIdx + 100);
        returnData.push(dateForChart);
        return returnData;
      }
    }
  }

  chartLoad(param){
    this.setState({
      chartLoading : param
    })
  }
  render() {
    const {
      page,
      count 
    } = this.state;
    const style = {
      display : "none"
    }
    return (
      <div className="container min">
        <header className="jumbotron">
        AI 고장진단 결과 조회
        </header>
        <div className="search-Bar contents04">
          <div className="amvh-selector form-group">
          <form onSubmit={this.handleSubmit}>
            <label>
              차량(호기) 선택
            </label>
              <select value={this.state.selectedVehicle} onChange={this.vehicleIdChange}>            
                {
                  this.state.allVehicleInfo &&
                  this.state.allVehicleInfo.map((el, idx) => {
                    return (
                      <option value={el.sdaid} key={el.sdaid}>{el.sdanm}</option>  
                    )
                  })
                }
              </select>
          </form>
          </div>
          <div className="gear-selector form-group">
          <form onSubmit={this.handleSubmit}>
            <label>
              부품 선택
            </label>
              <select value={this.state.selectedPart} onChange={this.vehiclePartChange}>            
                <option value="BEARING">베어링</option>
                <option value="ENGINE">엔진</option>
                <option value="GEARBOX">기어박스</option>
                <option value="WHEEL">휠</option>
              </select>
          </form>
          </div>
          <div className="datepicker-div form-group">
            <label>검색 기간</label>
            <div className="form-datepic">
              <div className="detepicker-div-start">
                <form className="datepicker-form" onSubmit={this.onFormSubmit}>
                  <div className="form-group">
                    <DatePicker 
                      selected={this.state.startDate}
                      onChange={this.startDateChange}
                      name="startDate"
                      dateFormat="yyyy/MM/dd"
                      locale={ko}
                    />
                  </div>
                </form>
                </div>
                <em>~</em>
                <div className="detepicker-div-end">
                  <form className="datepicker-form" onSubmit={this.onFormSubmit}>
                    <div className="form-group">
                      <DatePicker 
                        selected={this.state.endDate}
                        onChange={this.endDateChange}
                        minDate={this.state.startDate}
                        name="endDate"
                        dateFormat="yyyy/MM/dd"
                        locale={ko}
                      />
                    </div>
                  </form>
                </div>
              </div>
            </div>
            <button className="btn07" onClick={this.searchTroubleThings} >조회하기</button>
          </div>
            <div id="includeTroubleTable" className="contents05"  disabled={this.state.tableLoading}>  
            
            {/* 테이블만드는 곳 */}
            {this.state.tableLoading && (
              <div className="d-flex justify-content-center loading-box04">
                  <div className="spinner-border loading-in" role="status">
                      <span className="sr-only">Loading...</span>
                  </div>
              </div>
            )}

            </div>
            
            {/* </div> */}
            <div className="mt-3">
              <Pagination
                className="my-3"
                count={count}
                page={page}
                siblingCount={1}
                boundaryCount={1}
                variant="outlined"
                shape="rounded"
                onChange={this.handlePageChange}
              />
              <button className="btn-do" onClick={this.downloadExcel} >다운로드</button>
            </div>
          {/* </div> */}
          <div id="analysisTable" className="chart">
          
          </div>
          {/* 차트만들기 */}
          {/* <div className="chart" disabled={this.state.chartLoading}>
          
          {this.state.chartLoading && (
              <div className="loading-box">
                  <div className="spinner-border loading-in" role="status">
                      <span className="sr-only">Loading...</span>
                  </div>
              </div>
            )}
            <div style={this.state.chartLoading ? style : null}>
            <canvas id="lineChart1" ref={this.lineChart1Ref} width="680px" height="300px" style={{display:'inline-block'}}/>
            <canvas id="lineChart2" ref={this.lineChart2Ref} width="680px" height="300px" style={{display:'inline-block'}}/>
            </div>
          </div> */}

      </div>
    );
  }
}

