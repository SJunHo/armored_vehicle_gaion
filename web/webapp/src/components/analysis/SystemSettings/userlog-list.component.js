import React, { Component } from "react";
import userService from "../../../services/login/user.service";
import Pagination from "@material-ui/lab/Pagination";
import {Link} from "react-router-dom";

export default class UserLogList extends Component {
  constructor(props) {
    super(props);

    this.getUserLogList= this.getUserLogs.bind(this);
    this.getRequestParams = this.getRequestParams.bind(this);
    this.handlePageChange = this.handlePageChange.bind(this);
    this.state = {
      content: "",
      userLogList: [],
      currentIndex: -1,
      page: 1,
      count: 0,
      pageSize: 10,
    };
  }

  componentDidMount(){
    this.getUserLogs();
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

  getUserLogs(){
    const {page, pageSize} = this.state;
    const params = this.getRequestParams(page, pageSize);

    userService.getUserLogList(params)
    .then((response) => {
      const { userLogList, paging } = response.data;
      this.setState({
        userLogList: userLogList,
        count : paging.totalPageCount,
      });
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  handlePageChange(event, value) {
    this.setState({
        page: value,
      },() => {
        this.getUserLogList();
      });
  }


  render() {
    const {
      page,
      count,
    } = this.state;

    return (
      <div className="container">
        <header className="jumbotron">
         사용자 로그인 기록
        </header>
        
        <div className="table-div table03">
        <table>
          <thead>
              <tr>
                <td className="col-6">사용자ID</td>
                <td className="col-6">접속시간</td>
              </tr>
          </thead>
          <tbody>
          {
          this.state.userLogList && 
            this.state.userLogList.map((item, index) => {
              return(
              <tr key={index}>
                <td>{item.userid}</td>
                <td>{item.logindt}</td>
              </tr>
              );
            })}
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
