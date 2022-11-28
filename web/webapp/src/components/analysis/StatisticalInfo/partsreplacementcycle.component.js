import React, { Component } from "react";
import partsreplacementcycleService from "../../../services/analysis/partsreplacementcycle.service";
import "../../../../node_modules/react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";
import {ko} from "date-fns/locale";
import Moment from 'moment';

export default class PartsReplacementCycle extends Component {
  constructor(props) {
    super(props);
    this.state = {
      changedDivsCode: "",
      changedBrgdbnCode: "",
      chadgedVehiclecode: "",

      PrListcode: "",

      cmpntsrplchistry: [],
      // cmpntsrplchistry: "",

      snList: [],
      bnList: [],
      vnList: [],
      prList: [],

      snsrList: [],       //센서
      snsrListcode: "",

      cmncdList: [],
      cmncdListcode: "",
      cmncdListExpln: "",
      
      //changeEngineer: "",     //정비자
      Reasonforexchange: "",  //교환사유

      workrinput: "",

      msginput: "",

      startDate: new Date(),
      endDate: new Date(),
      rplcdate: new Date(),

      date: "",

      clickList: false,
      changedVehicleName: "",

      searchLoading : false,
      clickLoading : false,

      clickedTr : null,
    };
    this.starthandleChange = this.starthandleChange.bind(this);
    this.endhandleChange = this.endhandleChange.bind(this);
    this.rplcdatehandleChange = this.rplcdatehandleChange.bind(this);

    this.clickSearch = this.clickSearch.bind(this);
    this.clickAdd = this.clickAdd.bind(this);
  }

  componentDidMount() {
    this.getDivsList();
    this.getCmncdList();
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.changedDivsCode !== this.state.changedDivsCode) {
      this.getBnList();
    }
    if (prevState.changedBrgdbnCode !== this.state.changedBrgdbnCode) {
      this.getVnList();
    }
    if (prevState.changedVehicleName !== this.state.changedVehicleName) {
      this.getCmncdList();
    }
  }
  //사단
  getDivsList() {
    partsreplacementcycleService.getDivsList()
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
  // 연대 & 대대
  getBnList() {
    partsreplacementcycleService.getBnList(this.state.changedDivsCode)
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
  // 차량
  getVnList() {
    partsreplacementcycleService.getVnList(this.state.changedBrgdbnCode)
      .then((response) => {
        let resList = response.data;
        this.setState({
          vnList: response.data,
          chadgedVehiclecode: resList[0].sdaid
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  getCmncdList() {
    partsreplacementcycleService.getCmncdList()
      .then((response) => {
        console.log(response.data);
        let cmncdList = response.data;
        this.setState({
          cmncdList: response.data,
          cmncdListExpln: cmncdList[0].expln,
          cmncdListcode: cmncdList[0].code
        });
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

  endhandleChange(date) {
    this.setState({
      endDate: date
    })
    console.log(this.state.endDate)
  }

  rplcdatehandleChange(date) {
    this.setState({
      rplcdate: date
    })
    console.log(this.state.rplcdate)
  }

  //검색
  clickSearch() {
    this.setState({
      searchLoading : true,
    });
    var data = {
      sdaid: this.state.chadgedVehiclecode, //차량
      startDate: Moment(this.state.startDate).format("YYYY-MM-DD"), //시작 날짜
      endDate: Moment(this.state.endDate).format("YYYY-MM-DD"), //끝 날짜
      divscode: this.state.changedDivsCode, //사단
      brgdbncode: this.state.changedBrgdbnCode, //소속연대
    }

    partsreplacementcycleService.search(data)
      .then((response) => {
        this.setState({
          prList: response.data,
          searchLoading : false,
        });
      })
      .catch((e) => {
        console.log(e);
      });
  }

  clickAdd() {
    var data = {
        sdaid: this.state.changedVehicleName,
        expln: this.state.cmncdListExpln,
        grid: this.state.cmncdListcode,
        // code: this.state.cmncdList,
        workr: this.state.workrinput,
        rplcdate: this.state.rplcdate,
        msg: this.state.msginput,
      }
    console.log(data);
    partsreplacementcycleService.add(data)
      .then((response) => {
        this.setState({
          cmpntsrplchistry: response.data
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  clickList(data, idx) {

    const trElement = document.getElementById('tr' + idx);

    if(this.state.clickedTr !== null){
      this.state.clickedTr.classList.remove("highlight");
      trElement.classList.add("highlight");
    }else{
      trElement.classList.add("highlight");
    }
    this.setState({
      changedVehicleName: data.sdaid,
      //cmncdListcode: data.expln
      cmncdListcode: data.grid,
      cmncdListItemName: data.expln,
      clickLoading : true,
      clickedTr : trElement
    }, () => { console.log(this.state.cmncdListcode); });
    partsreplacementcycleService.getHistory(data)
      .then((response) => {
        this.setState({
          cmpntsrplchistry: response.data,
          clickLoading : false,
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">부품 교환주기 정보</header>

        <div className="contents04">
          <div className="form-group">
            <label htmlFor="description">소속사단</label>
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
            <label htmlFor="description">소속연대&대대</label>
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
            <select value={this.state.chadgedVehiclecode || ""}
              onChange={(e) => this.setState({ chadgedVehiclecode: e.target.value })}>
              {this.state.vnList.map((option) => (
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
                  onChange={this.endhandleChange}
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

        <div className="contents05" disabled={this.state.searchLoading}>
        {this.state.searchLoading && (
                <div className="d-flex justify-content-center loading-box04">
                    <div className="spinner-border loading-in" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
            )}
          <table>
            <thead>
              <tr>
                <td>사단</td>
                <td>연대<br/>(대대)</td>
                <td>차량<br/>(호기)</td>
                <td>발생시간</td>
                <td>발생센서</td>
                <td>발생값<br/>(km)</td>
                <td>기준값<br/>(km)</td>
                <td>발생값<br/>(일)</td>
                <td>기준값<br/>(일)</td>
                <td>발생값<br/>(횟수)</td>
                <td>기준값<br/>(횟수)</td>
                <td>교환정보</td>
              </tr>
            </thead>

            <tbody>
              {
                this.state.prList &&
                this.state.prList.map((element, index) => {
				  const dttime = Moment(element.dttime).format("YYYY-MM-DD");
                  return (
                    <tr key={index} id={"tr" + index} onClick={(e) => this.clickList(element, index)}>
                      <td>{element.divs}</td>
                      <td>{element.brgd + " " + element.bn}</td>
                      <td>{element.sdaid}</td>
                      <td>{dttime}</td>
                      <td>{element.expln}</td>
                      <td>{element.stdvle}</td>
                      <td>{element.stdval}</td>
                      <td>{element.prdvle}</td>
                      <td>{element.prdval}</td>
                      <td>{element.nmvle}</td>
                      <td>{element.nmval}</td>
                      <td>{element.msg}</td>
                    </tr>
                  );
                })
              }
            </tbody>
          </table>
        </div>


        <div className="mt30">
          <header className="jumbotron">
            부품 교환주기 등록
          </header>
        </div>

        <div className="contents04">
          <div className="form-group">
            <label htmlFor="description">선택차량</label>
            <input
              type="text"
              className="form-control info-register"
              value={this.state.changedVehicleName || ""}
              onChange={(e) => this.setState({ changedVehicleName: e.target.value })}
              name="description"
              readOnly
            />
          </div>

          <div className="form-group">
            <label htmlFor="description">교환부품명</label>
            <select value={this.state.cmncdListcode || ""}
              onChange={(e) => this.setState({ cmncdListcode: e.target.value })}>
              {this.state.cmncdList.map((option) => (
                <option key={option.code}
                  value={option.code}>
                  {option.expln}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="description">정비자</label>
            <input
              type="text"
              className="form-control info-register"
              id="engineer"
              required
              value={this.state.workrinput || ""}
              onChange={(e) => this.setState({ workrinput: e.target.value })}
              name="description"
            />
          </div>
          <div className="form-group">
          <form className="start-form" onSubmit={this.onFormSubmit}>
            
              <p>교환일</p>
              <div className="form-group form-datepic ">
                <DatePicker
                  selected={this.state.rplcdate}
                  onChange={this.rplcdatehandleChange}
                  name="rplcdate"
                  dateFormat="yyyy/MM/dd"
                  selectsStart
                  rplcdate={this.state.rplcdate}
                // startDate={this.state.startDate}
                // endDate={this.state.endDate}
                />
              </div>
           
          </form>
          </div>
          <div className="form-group">
            <label htmlFor="description">교환사유</label>
            <input
              type="text"
              className="form-control info-register"
              id="exchange"
              required
              value={this.state.msginput || ""}
              onChange={(e) => this.setState({ msginput: e.target.value })}
              name="description"
            />
          </div>

          <button className="btn07" onClick={this.clickAdd}>교환</button>
        </div>

        <div className="contents05" disabled={this.state.clickLoading}>
        {this.state.clickLoading && (
                <div className="d-flex justify-content-center loading-box04">
                    <div className="spinner-border loading-in" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
            )}
          <table>
            <thead>
              <tr>
                <td>교환차량</td>
                <td>교환부품명</td>
                <td>정비자</td>
                <td>교환일</td>
                <td>교환사유</td>
              </tr>
            </thead>

            <tbody>
              {
                this.state.cmpntsrplchistry &&
                this.state.cmpntsrplchistry.map((element, index) => {
					const rplcdate = Moment(element.rplcdate).format("YYYY-MM-DD");
                  return (
                    <tr key={index} onClick={(e) => this.clickAdd(element)}>
                      <td>{element.sdaid}</td>
                      <td>{element.grid}</td>
                      <td>{element.workr}</td>
                      <td>{rplcdate}</td>
                      <td>{element.msg}</td>
                    </tr>
                  );
                })
              }
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}
