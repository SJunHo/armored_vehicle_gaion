import React, { Component } from "react";
import UserService from "../../../services/login/user.service";
import TreeMenu, { ItemComponent } from "react-simple-tree-menu";
import '../../../../node_modules/react-simple-tree-menu/dist/main.css';
import { Bar } from "@nivo/bar";
import DatePicker from "react-datepicker";
import statisticalService from "../../../services/analysis/statistical.service";
import "../../../../node_modules/react-datepicker/dist/react-datepicker.css";
import {ko} from "date-fns/locale";
import Table from "./statisticalTable.component";
import amvhimg from "../../../amvhimg.png";
import '../../../css/fonts.css';
import '../../../css/style.css';

 export default class Statistical extends Component {
  constructor(props) {
    super(props);
    this.state = {
      content: "",
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
  }

  componentDidMount() {
    statisticalService.getTree().then(
      response => {
        this.setState({
          treeArray: response.data
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
  
    statisticalService.getGraph(0,1,this.state.startDate).then(
      response => {
        this.setState({
          graphData : response.data
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

    statisticalService.getTable(0,1,this.state.startDate).then(
      response => {
        this.setState({
          tableData : response.data
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
  }

  getGraphData(level,url,date){
    statisticalService.getGraph(level,url, date).then(
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
    statisticalService.getTable(level,url, date).then(
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
    console.log(param);
    this.setState({
      graphLevel : param.level,
      graphUrl : param.url
    });

    if(param.sda==null){
      this.getTableData(param.level,param.url,this.state.startDate);
      this.getGraphData(param.level,param.url,this.state.startDate);
    }
    if(param.level >= 3){
      window.location.href = "/searchEachInfo/"+param.label;
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
            value : two[1]
          }
          avgsdt.push(avgs);
        });
      }

      let engnnldnrate = [];
      if(engnnldnrateData!= null){
        Object.entries(engnnldnrateData).forEach(two => {
            let engnnldn = {
            bn : two[0],
            value : two[1]
          }
          engnnldnrate.push(engnnldn);
        });
      }

      let mvmtdstc = [];
      if(mvmtdstcData!= null){
        Object.entries(mvmtdstcData).forEach(two => {
            let mvmtd = {
            bn : two[0],
            value : two[1]
          }
          mvmtdstc.push(mvmtd);
        });
      }

      let mvmttime = [];
      if(mvmttimeData!= null){
        Object.entries(mvmttimeData).forEach(two => {
            let mvmtt = {
            bn : two[0],
            value : two[1]
          }
          mvmttime.push(mvmtt);
        });
      }

      this.setState({
        avgsdtGraphData : avgsdt,
        engnnldnrateGraphData : engnnldnrate,
        mvmtdstcGraphData : mvmtdstc,
        mvmttimeGraphData : mvmttime
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
      // let amvhTableData = new Map();
      // let sdaidArray = [];
      // let sdaStateArray = [];
      // let sdaStateString = "";
      // Object.entries(tabledata[1]).forEach((a)=>{
      //   sdaidArray.push(a[0].split("/")[0]);
      // })
      // let sdaidArrayResult = sdaidArray.filter((v, i) => sdaidArray.indexOf(v) === i);
      // sdaidArrayResult.forEach((r)=>{
      //   Object.entries(tabledata[1]).forEach((a)=>{
      //     console.log(a[0].split("/")[0]);
      //     if(r === a[0].split("/")[0]){
      //       sdaStateString += a[0].split("/")[1];
      //     }
      //   })
      //   amvhTableData.set(r,sdaStateString);
      //   sdaStateArray.push(sdaStateString);
      //   sdaStateString = "";
      // })

      this.setState({
        tableResult : tableResultArray,
        amvhTable : tabledata[1]
      },()=>{
        console.log(this.state.amvhTable);
      });
      
    }else{
      Object.entries(table[0]).forEach(tab=>{
        tabledata = tab[1];
      });
      console.log(tabledata);
      this.setState({
        tableResult : tabledata,
        amvhTable : ""
      });
    }
    console.log(this.state.tableResult);
  }

  handleChange(date){
    this.setState({
      startDate : date
    })
  }

  handleSubmit(e){
    e.preventDefault();
    let main = this.state.startDate
    console.log(main.format('L'));
  }

  getRandomColor(){
    let letters = '23456789ABD';
    let color = '#';
    for(var i = 0; i < 6; i ++){
      color += letters[Math.floor(Math.random() * 11)];
    }
    return color;
  }

  render() {

    const color = ({id}) => (this.getRandomColor());
    return (
      <div className="container">
        <div className="sub_title">
              <h1>차륜형 장갑차 센서데이터 수집, 분석 체계</h1>
        </div>
        <div className="row">
          <div className="Tree col-2">
          <TreeMenu data={this.state.treeArray}
              initialOpenNodes={['tree','tree/2','tree/3','tree/4']}
              >
            {({ items }) => (
              <div>
                <ul className="tree-item-group">
                {items.map(({key, ...props }) => (
                    <ul key={key} className={
                      props.label.includes("25사단") 
                          ? (props.isOpen ? 'Opened index1' : 'Closed index1') 
                          : props.label.includes("37사단") 
                                ? (props.isOpen ? 'Opened index2' : 'Closed index2') 
                                : props.label.includes("군수교") 
                                ? (props.isOpen ? 'Opened index3' : 'Closed index3') 
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
        <div className="contents">
          <div className="stable col-9">
            <div className="detepicker-div">
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
                this.state.tableResult &&
                <Table columns={this.state.columns} data={this.state.tableResult} /> 
              }
            {
              this.state.amvhTable &&

              Object.entries(this.state.amvhTable).map(amvh => {
                  return <div className="amvh-div" key={amvh[0]}>
                            <div className="amvh-img">
                            <img src={amvhimg} alt="amvh"></img>
                            <p>{amvh[0]}</p>
                            </div>
                            <div className="amvh-button">
                            {
                              amvh[1].includes("NN")
                              ? <button className="btn btn-light" >정상</button>
                              : <button className="btn btn-light" disabled>정상</button>
                            }

                            {
                              amvh[1].includes("B")
                              ? <button className="btn btn-danger" >고장</button>
                              : <button className="btn btn-light" disabled>고장</button>
                            }

                            {
                              amvh[1].includes("O")
                              ? <button className="btn btn-danger" >이상</button>
                              : <button className="btn btn-light" disabled>이상</button> 
                            }
                            </div>
                          </div>
              })
            }
            </div>
          </div>
          <div className="sgraph col-9">
                  <div className="graph-box">
                    <p>운행거리</p>
                    <Bar
                      width={230}
                      height={200}
                      margin={{ top: 30, right: 5, bottom: 30, left: 10 }}
                      data={this.state.avgsdtGraphData}
                      indexBy="bn"
                      keys={["value"]}
                      colors={color}
                      enableGridX={true}
                      enableLabel={false}
                      />
                  </div>
                  <div className="graph-box">
                    <p>운행시간</p>
                    <Bar
                      width={230}
                      height={200}
                      margin={{ top: 30, right: 5, bottom: 30, left: 5 }}
                      data={this.state.engnnldnrateGraphData}
                      indexBy="bn"
                      keys={["value"]}
                      colors={color}
                      labelSkipWidth={5}
                      labelSkipHeight={5}
                      enableGridX={true}
                      enableLabel={false}
                    />
                  </div>
                  <div className="graph-box">
                    <p>평균속력</p>
                    <Bar
                      width={230}
                      height={200}
                      margin={{ top: 30, right: 5, bottom: 30, left: 5 }}
                      data={this.state.mvmtdstcGraphData}
                      indexBy="bn"
                      keys={["value"]}
                      colors={color}
                      enableGridX={true}
                      enableLabel={false}
                    />
                  </div>
                  <div className="graph-box">
                    <p>엔진 공회전 비율</p>
                    <Bar
                      width={230}
                      height={200}
                      margin={{ top: 30, right: 10, bottom: 30, left: 5 }}
                      data={this.state.mvmttimeGraphData}
                      indexBy="bn"
                      keys={["value"]}
                      colors={color}
                      enableGridX={true}
                      enableLabel={false}
                    />
                  </div>
                
          </div> 
        </div>
      </div>            
      </div>
    );
  }
}
