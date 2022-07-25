import React, { Component } from "react";
import { Redirect } from 'react-router-dom';

import { connect } from "react-redux";

import { logout } from "../../actions/login/auth";

class LogOutComp extends Component {
  constructor(props) {
    super(props);

    this.state = {
    };
  }

  componentDidMount(){
    const { dispatch } = this.props;
    dispatch(logout());
  }

  render() {
    const { isLoggedIn, message } = this.props;

    if (!isLoggedIn) {
      return <Redirect to="/" />;
    }

    return (
        <div>
        로그아웃 페이지
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

export default connect(mapStateToProps)(LogOutComp);
