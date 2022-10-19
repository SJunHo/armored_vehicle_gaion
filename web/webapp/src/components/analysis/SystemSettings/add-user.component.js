import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import { connect } from "react-redux";
import { register } from "../../../actions/login/auth";
import userService from "../../../services/login/user.service";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
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

const vtelno1 = (value) =>{
  if (value.length < 10 || value.length > 40) {
    return (
      <div className="alert alert-danger" role="alert">
        The phonenum must be between 10 and 40 characters.
      </div>
    );
  }
};

const vtelno2 = (value) =>{
  if (value.length < 10 || value.length > 40) {
    return (
      <div className="alert alert-danger" role="alert">
        The phonenum must be between 10 and 40 characters.
      </div>
    );
  }
};

const vsrvno = (value) =>{
  if (value.length < 4 || value.length > 10) {
    return (
      <div className="alert alert-danger" role="alert">
        The vsrvno must be between 4 and 10 characters.
      </div>
    );
  }
};

const vrnkcd = (value) =>{
  if (value.length < 2 || value.length > 10) {
    return (
      <div className="alert alert-danger" role="alert">
        The vrnkcd must be between 2 and 10 characters.
      </div>
    );
  }
};

const vrspofc = (value) =>{
  if (value.length < 2 || value.length > 6) {
    return (
      <div className="alert alert-danger" role="alert">
        The vrspofc must be between 2 and 6 characters.
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
    this.onChangePassword = this.onChangePassword.bind(this);
    this.onChangeDivs = this.onChangeDivs.bind(this);
    this.onChangeBrgdbn = this.onChangeBrgdbn.bind(this);
    this.getBrgnbnList = this.getBrgnbnList.bind(this);
    this.onChangeTelno1 = this.onChangeTelno1.bind(this);
    this.onChangeTelno2 = this.onChangeTelno2.bind(this);
    this.onChangeSrvno = this.onChangeSrvno.bind(this);
    this.onChangeRnkcd = this.onChangeRnkcd.bind(this);
    this.onChangeRspofc = this.onChangeRspofc.bind(this);

    this.state = {
      userid: "",
      pwd: "",
      name: "",
      divs : "",
      brgd : "",
      bn : "",

      rnkcd: "",
      rspofc: "",
      srvno: "",

      telno1: "",
      telno2: "",
      usrth: "",

      divsList : [],
      brgdbnList : [],
      brgdbn : "",

      successful: false,
    };
  }

  componentDidMount(){
    userService.getDivsList().then((response) => {
      this.setState({
        divsList : response.data,
        divs : response.data[0].expln,
      })
    });

  }

  componentDidUpdate(prevProps, prevState){
    if(prevState.divs !== this.state.divs){
      this.getBrgnbnList(this.state.divs);
    }
  }

  getBrgnbnList(value){
    userService.getBnList(value)
    .then((response)=>{
      this.setState({
        brgdbnList : response.data,
        brgdbn : response.data[0].trinfoname,
      })
    });
  }

  onChangeId(e){
    this.setState({
      userid: e.target.value,
    });
  }

  onChangeUsername(e) {
    this.setState({
      name: e.target.value,
    });
  }

  onChangePassword(e) {
    this.setState({
      pwd: e.target.value,
    });
  }

  onChangeDivs(e){
    this.setState({
      divs : e.target.value
    },()=>{this.getBrgnbnList(this.state.divs)});
  }

  onChangeBrgdbn(e){
    this.setState({
      brgdbn : e.target.value
    });
  }

  onChangeRnkcd(e){
    this.setState({
      rnkcd : e.target.value
    });
  }

  onChangeRspofc(e){
    this.setState({
      rspofc : e.target.value
    });
  }

  onChangeSrvno(e){
    this.setState({
      srvno : e.target.value
    });
  }

  onChangeTelno1(e){
    this.setState({
      telno1 : e.target.value
    });
  }

  onChangeTelno2(e){
    this.setState({
      telno2 : e.target.value
    });
  }

  handleRegister(e) {
    e.preventDefault();

    this.setState({
      successful: false,
    });

    this.form.validateAll();

    let brgdbn = this.state.brgdbn;
    let brgd = null;
    let bn = null;
    if(brgdbn.includes(" ")){
      brgd = brgdbn.split(" ")[0];
      bn = brgdbn.split(" ")[1];
    }else{
      bn = brgdbn;
    }

    var data = {
      userid : this.state.userid,
      name : this.state.name,
      pwd : this.state.pwd,
      usrth : this.state.usrth,
      rnkcd : this.state.rnkcd,
      srvno : this.state.srvno,
      divs : this.state.divs,
      brgd : brgd,
      bn : bn,
      rspofc : this.state.rspofc,
      telno1 : this.state.telno1,
      telno2 : this.state.telno2,
    }
    if (this.checkBtn.context._errors.length === 0) {
      this.props
        .dispatch(
          register(data)
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
                  <label htmlFor="pwd">비밀번호</label>
                  <Input
                    type="pwd"
                    className="form-control"
                    name="pwd"
                    value={this.state.pwd}
                    onChange={this.onChangePassword}
                    validations={[required, vpassword]}
                  />
                </div> 

                <div className="form-group">
                  <label htmlFor="description">사단</label>
                  <select value={this.state.divs || ""}
                    onChange={(e) => this.onChangeDivs(e)}>
                    {this.state.divsList.map((option) => (
                      <option key={option.expln}
                        value={option.expln}>
                        {option.expln}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="form-group">
                  <label htmlFor="description">연대&부대</label>
                  <select value={this.state.brgdbn || ""}
                    onChange={(e) => this.onChangeBrgdbn(e)}>
                    {this.state.brgdbnList.map((option) => (
                      <option key={option.trinfoname}
                        value={option.trinfoname}>
                        {option.trinfoname}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="form-group">
                  <label htmlFor="telno1">핸드폰1</label>
                  <Input
                    type="telno1"
                    className="form-control"
                    name="telno1"
                    value={this.state.telno1}
                    onChange={this.onChangeTelno1}
                    validations={[required, vtelno1]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="telno2">핸드폰2</label>
                  <Input
                    type="telno2"
                    className="form-control"
                    name="telno2"
                    value={this.state.telno2}
                    onChange={this.onChangeTelno2}
                    validations={[required, vtelno2]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="srvno">군번</label>
                  <Input
                    type="srvno"
                    className="form-control"
                    name="srvno"
                    value={this.state.srvno}
                    onChange={this.onChangeSrvno}
                    validations={[required, vsrvno]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="rnkcd">계급</label>
                  <Input
                    type="rnkcd"
                    className="form-control"
                    name="rnkcd"
                    value={this.state.rnkcd}
                    onChange={this.onChangeRnkcd}
                    validations={[required, vrnkcd]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="rspofc">직책</label>
                  <Input
                    type="rspofc"
                    className="form-control"
                    name="rspofc"
                    value={this.state.rspofc}
                    onChange={this.onChangeRspofc}
                    validations={[required, vrspofc]}
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
