import React, { Component } from "react";
import { Redirect } from 'react-router-dom';

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import { connect } from "react-redux";
import { login } from "../../actions/login/auth";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

class Login extends Component {
  constructor(props) {
    super(props);
    this.handleLogin = this.handleLogin.bind(this);
    this.onChangeId = this.onChangeId.bind(this);
    this.onChangeUsername = this.onChangeUsername.bind(this);
    this.onChangePassword = this.onChangePassword.bind(this);

    this.state = {
      id: "",
      username: "",
      password: "",
      loading: false,
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

  onChangePassword(e) {
    this.setState({
      password: e.target.value,
    });
  }

  handleLogin(e) {
    e.preventDefault();

    this.setState({
      loading: true,
    });

    this.form.validateAll();

    const { dispatch, history } = this.props;

    if (this.checkBtn.context._errors.length === 0) {
      dispatch(login(this.state.id, this.state.password))
        .then(() => {
          history.push("/statistical");
          window.location.reload();
        })
        .catch(() => {
          this.setState({
            loading: false
          });
        });
    } else {
      this.setState({
        loading: false,
      });
    }
  }

  render() {
    const { isLoggedIn, message } = this.props;

    if (isLoggedIn) {
      return <Redirect to="/statistical" />;
    }

    return (
      <div className="col-md-12">
        <div className="card card-container">
          <div className="card-in">
            <div className="logo">
              <a href="#none" className="logo" title="tcms 로고"></a>
              <p>차륜형 장갑차 센서데이터 분석 시스템</p>
            </div>
                

            <Form
              onSubmit={this.handleLogin}
              ref={(c) => {
                this.form = c;
              }}
            >
              <div className="form-group">
                <label htmlFor="id">Id</label>
                <Input
                  type="text"
                  className="form-control-style"
                  name="id"
                  placeholder="ID"
                  value={this.state.id}
                  onChange={this.onChangeId}
                  validations={[required]}
                />
              </div>

              <div className="form-group">
                <label htmlFor="password">Password</label>
                <Input
                  type="password"
                  className="form-control-style"
                  name="password"
                  placeholder="Password"
                  value={this.state.password}
                  onChange={this.onChangePassword}
                  validations={[required]}
                />
              </div>

              <div className="form-group">
                <button
                  className="btn btn02 btn-primary btn-block"
                  disabled={this.state.loading}
                >
                  {this.state.loading && (
                    <span className="spinner-border spinner-border-sm"></span>
                  )}
                  <span>로그인</span>
                </button>
              </div>
              {/* <button
                  className="btn btn03 btn-primary btn-block"
                  disabled={this.state.loading}
                  onClick={()=> this.props.history.push("/register")}
                >
                  <span>회원가입</span>
                </button> */}
              {message && (
                <div className="form-group">
                  <div className="alert alert-danger" role="alert">
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
        </div>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { isLoggedIn } = state.auth;
  const { message } = state.message;
  return {
    isLoggedIn,
    message
  };
}

export default connect(mapStateToProps)(Login);
