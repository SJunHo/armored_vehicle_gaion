import React, { Component } from "react";
import ReactDOM from 'react-dom';

import {ko} from "date-fns/locale";
import DatePicker from "react-datepicker";
import "../../../css/statusdatalookup.css";

import StatusDataLookupDataService from "../../../services/analysis/statusdatalookup.service";
import monitorremainingService from "../../../services/analysis/monitorremaining.service";
import Pagination from "@material-ui/lab/Pagination";
import TableByRemaining from "./tableByRemaining.component";

export default class MonitorRemainingUsefulLife extends Component {
  constructor(props) {
    super(props);

    this.state = {
      startDate : new Date(),
      endDate : new Date(),
      selectedVehicle : "",
      allVehicleInfo: [],
      selectedPart: "ber",
      page: 1,
      count: 0,
      pageSize: 10,

      lifeList: [],
      loading: false,

    };
    this.handleSubmit = this.handleSubmit.bind(this);

    this.vehicleIdChange = this.vehicleIdChange.bind(this);
    this.vehiclePartChange = this.vehiclePartChange.bind(this);
    this.startDateChange = this.startDateChange.bind(this);
    this.endDateChange = this.endDateChange.bind(this);
    this.searchLifeThings = this.searchLifeThings.bind(this);

    this.handlePageChange = this.handlePageChange.bind(this);
    this.changeClickBtn = this.changeClickBtn.bind(this);

    this.getAllVehicleInfo = this.getAllVehicleInfo.bind(this);

  }

  componentDidMount(){
    const startDate = this.state.startDate;
    const endDate = this.state.endDate;

    startDate.setDate(endDate.getDate() - 7);

    this.setState({
      startDate : startDate,
    });
    this.getAllVehicleInfo();
  }

  getAllVehicleInfo(){    //모든차량 정보가져오는 함수
    StatusDataLookupDataService.getAllVehicleInfo()
    .then((response) => {
      this.setState({
        allVehicleInfo: [...response.data],
        selectedVehicle: response.data[0].sdaid,
      }, () => {
        console.log(this.state.allVehicleInfo);
        console.log(this.state.selectedVehicle);

        // this.searchTroubleThings();
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
    }, () => {
      console.log(this.state.selectedVehicle);
    });
  }

  vehiclePartChange(res){
    this.setState({
      selectedPart: res.target.value,
    }, () => {
      console.log(this.state.selectedPart);
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

  searchLifeThings(){
    this.setState({
      loading: true,
    });

    const {selectedPart, page, pageSize, selectedVehicle, startDate, endDate} = this.state;

    const parentElement = document.getElementById('includeTroubleTable');

    let data = {
      part : selectedPart,
      page : page,
      size : pageSize,
      sdaid : selectedVehicle,
      startDate : startDate,
      endDate : endDate,
    }

    switch(this.state.selectedPart){
      case 'ber':
        monitorremainingService.searchBerlife(data)
        .then((response) => {
          const { lifeList, paging} = response.data;
          this.setState({
            lifeList: lifeList,
            count: paging.totalPageCount,
            loading : false,
          }, () => {
            ReactDOM.render(
            <TableByRemaining data={this.state.lifeList} partName={this.state.selectedPart} func={this.changeClickBtn} />, 
            parentElement);
          });
        })
        .catch((e) => {
          console.log(e);
        })
        break;

      case 'eng':
        monitorremainingService.searchEnglife(data)
        .then((response) => {
          const { lifeList, paging} = response.data;
          this.setState({
            lifeList: lifeList,
            count: paging.totalPageCount,
            loading : false,
          }, () => {

            ReactDOM.render(
            <TableByRemaining data={this.state.lifeList} partName={this.state.selectedPart} func={this.changeClickBtn}/>
            , parentElement);
          });
        })
        .catch((e) => {
          console.log(e);
        })
        break;

      case 'grb':
        monitorremainingService.searchGrblife(data)
        .then((response) => {
          const { lifeList, paging} = response.data;
          this.setState({
            lifeList: lifeList,
            count: paging.totalPageCount,
            loading : false,
          }, () => {

            ReactDOM.render(
            <TableByRemaining data={this.state.lifeList} partName={this.state.selectedPart} func={this.changeClickBtn}/>
            , parentElement);
          });
        })
        .catch((e) => {
          console.log(e);
        })
        break;

      default:
        monitorremainingService.searchWhllife(data)
        .then((response) => {
          const { lifeList, paging} = response.data;
          this.setState({
            lifeList: lifeList,
            count: paging.totalPageCount,
            loading : false,
          }, () => {

            ReactDOM.render(
            <TableByRemaining data={this.state.lifeList} partName={this.state.selectedPart} func={this.changeClickBtn}/>
            , parentElement);
          });
        })
        .catch((e) => {
          console.log(e);
        })
    }
  }

  handlePageChange(event, value) {
    this.setState({
      page: value,
    }, () => {
      this.searchLifeThings();
    })
  }
  
  changeClickBtn(res) {
    console.log(res);
    this.setState({
      searchBtnClick: res
    });
  }

  render() {

    const {
      page,
      count 
    } = this.state;

    return (
      <div className="container min">
        <header className="jumbotron">
        잔존 수명 예지 결과 조회
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
	                <option value="ber">베어링</option>
	                <option value="eng">엔진</option>
	                <option value="grb">기어박스(감속기)</option>
	                <option value="whl">휠</option>
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
          <button className="btn07" onClick={this.searchLifeThings} >조회하기</button>
        </div>
        <div id="includeTroubleTable" className="contents05" disabled={this.state.loading}>  
            {/* 테이블만드는 곳 */}
            {this.state.loading && (
                  <div className="d-flex justify-content-center  loading-box04">
                      <div className="spinner-border loading-in" role="status">
                          <span className="sr-only">Loading...</span>
                      </div>
                  </div>
             )}
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
