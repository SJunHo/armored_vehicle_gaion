import React, { Component } from "react";
import userService from "../../../services/login/user.service";
import Pagination from "@material-ui/lab/Pagination";
import {Link} from "react-router-dom";

export default class ManageUsersList extends Component {
  constructor(props) {
    super(props);

    this.getUserList = this.getUserList.bind(this);
    this.getRequestParams = this.getRequestParams.bind(this);
    this.handlePageChange = this.handlePageChange.bind(this);
    this.handleRowClick = this.handleRowClick.bind(this);

    this.state = {
      content: "",
      userList : [],
      currentIndex: -1,
      page: 1,
      count: 0,
      pageSize: 10,

    };
  }
  componentDidMount(){
    this.getUserList();
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

  getUserList(){
    const {page, pageSize} = this.state;
    const params = this.getRequestParams(page, pageSize);

    userService.getUserList(params)
    .then((response) => {
      const { userList, paging } = response.data;
      this.setState({
        userList: userList,
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
        this.getCmncdList();
      });
  }

  handleRowClick(id){
    this.props.history.push(`/manageuser/${id}`);
  }

  render() {
    const {
      page,
      count,
    } = this.state;

    return (
      <div className="container">
        <header className="jumbotron">
        사용자 관리
        </header>
        <Link to={"/addUser"} className="btn btn-info">
            등록
        </Link>
        <table>
          <thead>
              <tr>
                <td>사용자 ID</td>
                <td>사용자명</td>
                <td>연락처</td>
                <td>소속</td>
                <td>군번</td>
                <td>계급</td>
                <td>권한</td>
              </tr>
          </thead>
          <tbody>
          {
          this.state.userList && 
            this.state.userList.map((item, index) => {
              return(
              <tr key={item.id} onClick={()=>this.handleRowClick(item.id)}>
                <td>{item.id}</td>
                <td>{item.username}</td>
                <td>{item.phonenum}</td>
                <td>{item.mltunit}</td>
                <td>{item.mltnum}</td>
                <td>{item.mltrank}</td>
                <td>{item.usrth === 'M' 
                            ? '분석가' : ( item.usrth === 'N' 
                            ? '사용자' : '관리자'
                        )}</td>
              </tr>
              );
            })}
          </tbody>
        </table>
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
