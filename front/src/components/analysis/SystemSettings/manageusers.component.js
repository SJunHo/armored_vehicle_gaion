import React, { Component } from "react";
import userService from "../../../services/login/user.service";

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const email = (value) => {
  if (!isEmail(value)) {
    return (
      <div className="alert alert-danger" role="alert">
        This is not a valid email.
      </div>
    );
  }
};

const vuserid = (value) => {
  if (value.length < 3 || value.length > 20) {
    return (
      <div className="alert alert-danger" role="alert">
        The userid must be between 3 and 20 characters.
      </div>
    );
  }
};

const vusername = (value) => {
  if (value.length < 3 || value.length > 20) {
    return (
      <div className="alert alert-danger" role="alert">
        The username must be between 3 and 20 characters.
      </div>
    );
  }
};

const vpassword = (value) => {
  if (value.length < 6 || value.length > 40) {
    return (
      <div className="alert alert-danger" role="alert">
        The password must be between 6 and 40 characters.
      </div>
    );
  }
};

const vphonenum = (value) =>{
  if (value.length < 10 || value.length > 40) {
    return (
      <div className="alert alert-danger" role="alert">
        The phonenum must be between 10 and 40 characters.
      </div>
    );
  }
};

const vmltnum = (value) =>{
  if (value.length < 4 || value.length > 10) {
    return (
      <div className="alert alert-danger" role="alert">
        The vmltnum must be between 4 and 10 characters.
      </div>
    );
  }
};

const vmltrank = (value) =>{
  if (value.length < 2 || value.length > 10) {
    return (
      <div className="alert alert-danger" role="alert">
        The vmltrank must be between 2 and 10 characters.
      </div>
    );
  }
};

const vmltunit = (value) =>{
  if (value.length < 2 || value.length > 6) {
    return (
      <div className="alert alert-danger" role="alert">
        The vmltunit must be between 2 and 6 characters.
      </div>
    );
  }
};

export default class ManageUsers extends Component {
  constructor(props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);
    this.onChangeId = this.onChangeId.bind(this);
    this.onChangeUsername = this.onChangeUsername.bind(this);
    this.onChangeEmail = this.onChangeEmail.bind(this);
    this.onChangePassword = this.onChangePassword.bind(this);
    this.onChangePhonenum = this.onChangePhonenum.bind(this);
    this.onChangeMltnum = this.onChangeMltnum.bind(this);
    this.onChangeMltrank = this.onChangeMltrank.bind(this);
    this.onChangeMltunit = this.onChangeMltunit.bind(this);

    this.state = {
      id: "",
      username: "",
      email: "",
      password: "",
      usrth: "",
      phonenum: "",
      mltrank: "",
      mltnum: "",
      mltunit: "",
      successful: false,
    };
  }
  componentDidMount(){
    this.getUserInfo(this.props.match.params.id);
  }
  onChangeId(e){
    this.setState({
      id: e.target.value,
    });
  }

  onChangeUsername(e) {
    this.setState({
      username: e.target.value,
    });
  }

  onChangeEmail(e) {
    this.setState({
      email: e.target.value,
    });
  }

  onChangePassword(e) {
    this.setState({
      password: e.target.value,
    });
  }

  onChangePhonenum(e){
    this.setState({
      phonenum : e.target.value,
    });
  }

  onChangeMltnum(e){
    this.setState({
      mltnum : e.target.value,
    });
  }

  onChangeMltunit(e){
    this.setState({
      mltunit : e.target.value,
    });
  }

  onChangeMltrank(e){
    this.setState({
      mltrank : e.target.value,
    });
  }

  getUserInfo(id){
    userService.get(id)
    .then((response) => {
      this.setState({
        id : response.data.id,
        username : response.data.username,
        password : response.data.password,
        email : response.data.email,
        usrth : response.data.usrth,
        phonenum : response.data.phonenum,
        mltrank : response.data.mltrank,
        mltnum : response.data.mltnum,
        mltunit : response.data.mltunit
      });
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  handleRegister() {
    this.form.validateAll();

    var data = {
      id : this.state.id,
      username: this.state.username,
      email: this.state.email,
      password: this.state.password,
      usrth: this.state.usrth,
      phonenum: this.state.phonenum,
      mltrank: this.state.mltrank,
      mltnum: this.state.mltnum,
      mltunit: this.state.mltunit,
    }
    userService.update(data)
    .then((response) => {
      window.location.href = "/manageusers";
      console.log(response.data);
    })
    .catch((e) => {
      console.log(e);
    });
  }

  deleteUser(){
    var result = window.confirm("정말 삭제하시겠습니까?");
      if(result){
      userService.delete(this.state.id)
      .then((response) => {
        alert("삭제되었습니다");
        window.location.href = "/manageusers";
      })
      .catch((e) => {
        console.log(e);
      });
    }
  }


  render() {
    const { message } = this.props;
    return (
      <div className="container">
        <Form
            onSubmit={this.handleRegister}
            ref={(c) => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="id">사용자ID</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="id"
                    value={this.state.id}
                    onChange={this.onChangeId}
                    validations={[required, vuserid]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="username">사용자 이름</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="username"
                    value={this.state.username}
                    onChange={this.onChangeUsername}
                    validations={[required, vusername]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="email">이메일</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="email"
                    value={this.state.email}
                    onChange={this.onChangeEmail}
                    validations={[required, email]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="password">비밀번호</label>
                  <Input
                    type="password"
                    className="form-control"
                    name="password"
                    value={this.state.password}
                    onChange={this.onChangePassword}
                    validations={[required, vpassword]}
                  />
                </div> 

                <div className="form-group">
                  <label htmlFor="phonenum">핸드폰</label>
                  <Input
                    type="phonenum"
                    className="form-control"
                    name="phonenum"
                    value={this.state.phonenum}
                    onChange={this.onChangePhonenum}
                    validations={[required, vphonenum]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="mltnum">군번</label>
                  <Input
                    type="mltnum"
                    className="form-control"
                    name="mltnum"
                    value={this.state.mltnum}
                    onChange={this.onChangeMltnum}
                    validations={[required, vmltnum]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="mltrank">계급</label>
                  <Input
                    type="mltrank"
                    className="form-control"
                    name="mltrank"
                    value={this.state.mltrank}
                    onChange={this.onChangeMltrank}
                    validations={[required, vmltrank]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="mltunit">소속</label>
                  <Input
                    type="mltunit"
                    className="form-control"
                    name="mltunit"
                    value={this.state.mltunit}
                    onChange={this.onChangeMltunit}
                    validations={[required, vmltunit]}
                  />
                </div>

                <div className="form-group">
              <label htmlFor="usrth">권한</label>
              <input type="radio"
                id="A"
                required
                checked={this.state.usrth === "A"}
                onChange={(e)=> this.setState({usrth : 'A'})}
                name="usrth"
              />관리자
              <input type="radio"
                id="M"
                required
                checked={this.state.usrth === "M"}
                onChange={(e)=> this.setState({usrth : 'M'})}
                name="usrth"
              />분석가
              <input type="radio"
                id="N"
                required
                checked={this.state.usrth === "N"}
                onChange={(e)=> this.setState({usrth : 'N'})}
                name="usrth"
              />사용자
            </div>
            </div>
            )}

            <button onClick={this.handleRegister} className="btn btn-success">
              수정
            </button>
            <button onClick={this.deleteUser} className="btn btn-danger">
              삭제
            </button>
          </Form>
      </div>
    );
  }
}
