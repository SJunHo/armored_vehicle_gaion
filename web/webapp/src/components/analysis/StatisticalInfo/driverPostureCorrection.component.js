import React, { Component } from "react";
import driverPostureCorrectionService from "../../../services/analysis/driverPostureCorrection.service";
import "../../../../node_modules/react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";
import Pagination from "@material-ui/lab/Pagination";
import {ko} from "date-fns/locale";

export default class driverPostureCorrection extends Component {
  constructor(props) {
    super(props);
    this.state = {
      changedDivsCode: "",
      changedBrgdbnCode: "",
      chadgedVehicleCode: "",

      snList: [],
      bnList: [],
      sdaList: [],
      diList: [],

      page: 1,
      count: 0,
      pageSize: 10,

      startDate: new Date(),
      endDate: new Date(),
      date: "",
    };
    this.starthandleChange = this.starthandleChange.bind(this);
    this.endhandlechange = this.endhandlechange.bind(this);
    this.clickSearch = this.clickSearch.bind(this);
    this.handlePageChange = this.handlePageChange.bind(this);

  }

  componentDidMount() {
    this.getDivsList();
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.changedDivsCode !== this.state.changedDivsCode) {
      this.getBnList();
    }
    if (prevState.changedBrgdbnCode !== this.state.changedBrgdbnCode) {
      this.getSdaList();
    }
  }
  getDivsList() {
    driverPostureCorrectionService.getDivsList()
      .then((response) => {
        let resList = response.data;
        this.setState({
          snList: response.data,
          changedDivsCode: resList[0].code
        });
      })
      .catch((e) => {
        console.log(e);
      });
  }

  getBnList() {
    driverPostureCorrectionService.getBnList(this.state.changedDivsCode)
      .then((response) => {
        let resList = response.data;
        this.setState({
          bnList: response.data,
          changedBrgdbnCode: resList[0].trinfocode
        });
        console.log(this.state.changedBrgdbnCode);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  getSdaList() {
    driverPostureCorrectionService.getSdaList(this.state.changedBrgdbnCode)
      .then((response) => {
        let resList = response.data;
        this.setState({
          sdaList: response.data,
          chadgedVehicleCode: resList[0].sdaid
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  starthandleChange(date) {
    this.setState({
      startDate: date
    })
    console.log(this.state.startDate)
  }

  endhandlechange(date) {
    this.setState({
      endDate: date
    })
    console.log(this.state.endDate)
  }

  getRequestParams(page, pageSize) {
    let params = {};

    if (page) {
      params["page"] = page;
    }

    if (pageSize) {
      params["size"] = pageSize;
    }

    return params;
  }
  clickSearch(){
    const {page, pageSize } = this.state;
    
    var data = {
      divscode : this.state.changedDivsCode, //사단
      brgdbncode : this.state.changedBrgdbnCode, //소속연대
      sdaid : this.state.chadgedVehicleCode, //차량
      startDate : this.state.startDate, //시작 날짜
      endDate : this.state.endDate, //끝 날짜
      page : page,
      size : pageSize,
    }
    console.log(data);
    driverPostureCorrectionService.search(data)
    .then((response) => {
      const { driverCorrectInfoList, paging } = response.data;
      this.setState({
        diList : driverCorrectInfoList,
        count: paging.totalPageCount,
      });
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  handlePageChange(event, value) {
    this.setState(
      {
        page: value,
      },
      () => {
        this.clickSearch();
      }
    );
  }
  render() {
    const {
      page,
      count,
    } = this.state;

    return (
      <div className="container">
        <header className="jumbotron">
          운전자 자세교정 정보
        </header>
        <div className="contents04">
          <div className="form-group">
            <label htmlFor="description">사단</label>
            <select onChange={(e) => this.setState({ changedDivsCode: e.target.value })}>
              {this.state.snList.map((option) => (
                <option key={option.code}
                  value={option.code}>
                  {option.expln}
                </option>
              ))}
            </select>
          </div>


          <div className="form-group">
            <label htmlFor="description">연대&대대</label>
            <select value={this.state.changedBrgdbnCode || ""}
              onChange={(e) => this.setState({ changedBrgdbnCode: e.target.value })}>
              {this.state.bnList.map((option) => (
                <option key={option.trinfocode}
                  value={option.trinfocode}>
                  {option.trinfoname}
                </option>
              ))}
            </select>
          </div>


          <div className="form-group">
            <label htmlFor="description">차량</label>
            <select value={this.state.chadgedVehicleCode || ""}
              onChange={(e) => this.setState({ chadgedVehicleCode: e.target.value })}>
              {this.state.sdaList.map((option) => (
                <option key={option.sdaid}
                  value={option.sdaid}>
                  {option.sdanm}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <form className="start-form" onSubmit={this.onFormSubmit}>
              <p>검색기간</p>
              <div className="form-group form-datepic">
                <DatePicker
                  selected={this.state.startDate}
                  onChange={this.starthandleChange}
                  name="startDate"
                  dateFormat="yyyy/MM/dd"
                  selectsStart
                  startDate={this.state.startDate}
                  endDate={this.state.endDate}
                  locale={ko}
                />
                <em>~</em>
                <DatePicker
                  selected={this.state.endDate}
                  onChange={this.endhandlechange}
                  name="endDate"
                  dateFormat="yyyy/MM/dd"
                  selectsEnd
                  startDate={this.state.startDate}
                  endDate={this.state.endDate}
                  minDate={this.state.startDate}
                  locale={ko}
                />
              </div>
            </form>
          </div>

          <button className="btn07" onClick={this.clickSearch}>조회</button>
        </div>
        <div className="contents05">
          <table>
            <thead>
              <tr>
                <td>사단</td>
                <td>연대(대대)</td>
                <td>차량(호기)</td>
                <td>발생시간</td>
                <td>발생센서</td>
                <td>발생값</td>
                <td>기준값</td>
                <td>교정정보</td>
              </tr>
            </thead>
            <tbody>
              {
                this.state.diList &&
                this.state.diList.map((element,index) => {
                  
                  return (
                    <tr key={index}>
                      <td>{element.divs}</td>
                      <td>{element.bn}</td>
                      <td>{element.sdanm}</td>
                      <td>{element.dttime}</td>
                      <td>{element.snsrnm}</td>
                      <td>{element.snsrvle}</td>
                      <td>{element.stdval}</td> 
                      <td>{element.msg}</td> 
                    </tr>
                  );
                })
              }
            </tbody>
          </table>
        </div>
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
        </div>
      </div>
    );
  }
}