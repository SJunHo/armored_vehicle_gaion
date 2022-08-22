import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";

import { connect } from "react-redux";
import { register } from "../../../actions/login/auth";

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

class AddUser extends Component {
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

  handleRegister(e) {
    e.preventDefault();

    this.setState({
      successful: false,
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      this.props
        .dispatch(
          register(this.state.id,this.state.username, this.state.email, 
                    this.state.password, this.state.usrth, this.state.phonenum, 
                    this.state.mltrank, this.state.mltnum, this.state.mltunit)
        )
        .then(() => {
          alert("등록되었습니다");
          window.location.href = "/manageusers";
        })
        .catch(() => {
          this.setState({
            successful: false,
          });
        });
    }
  }

  render() {
    const { message } = this.props;

    return (
      <div className="container">
        <header className="jumbotron">
        사용자정보 등록
        </header>  
          <Form
            onSubmit={this.handleRegister}
            ref={(c) => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <div className="contents02">
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
                  <div className="radio-box">
                      <div className="radio-use02">
                        <input type="radio"
                          id="A"
                          required
                          checked={this.state.usrth === "A"}
                          onChange={(e)=> this.setState({usrth : 'A'})}
                          name="usrth"
                        />
                        <label htmlFor="A">관리자</label>
                      </div>
                      <div className="radio-use02">
                        <input type="radio"
                          id="M"
                          required
                          checked={this.state.usrth === "M"}
                          onChange={(e)=> this.setState({usrth : 'M'})}
                          name="usrth"
                        />
                        <label htmlFor="M">분석가</label>
                      </div>
                      <div className="radio-use02">
                        <input type="radio"
                          id="N"
                          required
                          checked={this.state.usrth === "N"}
                          onChange={(e)=> this.setState({usrth : 'N'})}
                          name="usrth"
                        />
                        <label htmlFor="N">사용자</label>
                      </div>
                  </div>
              </div>
              <button onClick={this.saveCmncd} className="btn btn04 btn-success">
                등록
              </button>
            </div>
            )}

            {message && (
              <div className="form-group">
                <div className={ this.state.successful ? "alert alert-success" : "alert alert-danger" } role="alert">
                  {message}
                </div>
              </div>
            )}
            <CheckButton
              style={{ display: "none" }}
              ref={(c) => {
                this.checkBtn = c;
              }}
            />
          </Form>
        </div>
    );
  }
}

function mapStateToProps(state) {
  const { message } = state.message;
  return {
    message,
  };
}

export default connect(mapStateToProps)(AddUser);
