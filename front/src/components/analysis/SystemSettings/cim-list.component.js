import React, { Component } from "react";
import cimService from "../../../services/analysis/cim.service";
import Pagination from "@material-ui/lab/Pagination";
import {Link} from "react-router-dom";

export default class CIMList extends Component {
  constructor(props) {
    super(props);

    this.getCmncdList = this.getCmncdList.bind(this);
    this.getRequestParams = this.getRequestParams.bind(this);
    this.handlePageChange = this.handlePageChange.bind(this);
    this.handlePageSizeChange = this.handlePageSizeChange.bind(this);
    this.handleRowClick = this.handleRowClick.bind(this);
    this.state = {
      content: "",
      cmncdList: [],
      currentIndex: -1,
      page: 1,
      count: 0,
      pageSize: 10,
    };
  }

  componentDidMount(){
    this.getCmncdList();
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

  getCmncdList(){
    const {page, pageSize} = this.state;
    const params = this.getRequestParams(page, pageSize);

    cimService.getAll(params)
    .then((response) => {
      const { cmncdList, paging } = response.data;
      this.setState({
        cmncdList: cmncdList,
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

  handlePageSizeChange(event) {
    this.setState(
      {
        pageSize: event.target.value,
        page: 1
      },
      () => {
        this.retrieveVehicles();
      }
    );
  }

  handleRowClick(id){
    this.props.history.push(`/cim/${id}`);
  }

  render() {
    const {
      page,
      count,
    } = this.state;

    return (
      <div className="container">
        <header className="jumbotron">
        공통정보 관리
        </header>
        <Link to={"/cimAdd"} className="btn btn-info">
            등록
        </Link>
        <table>
          <thead>
              <tr>
                <td>코드명</td>
                <td>설명</td>
                <td>사용여부</td>
                <td>생성일</td>
                <td>수정일</td>
              </tr>
          </thead>
          <tbody>
          {
          this.state.cmncdList && 
            this.state.cmncdList.map((item, index) => {
              return(
              <tr key={item.cmncdid} onClick={()=>this.handleRowClick(item.cmncdid)}>
                <td>{item.groupcode}</td>
                <td>{item.expln}</td>
                <td>{item.crtdt}</td>
                <td>{item.mdfcdt}</td>
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
