import { connect } from "react-redux";
import React, { Component } from 'react';
import vehicleStatissticsService from "../../../services/analysis/vehicleStatistics.service";

import SensorBtn from "./createSensorBtn.component";
import BookmarkBtn from "./bookmarkBtn.component";

class ChoiceSensorModal extends Component {
    constructor(props) {
        super(props);
        const {user} = this.props;
        this.getColumnForBtn = this.getColumnForBtn.bind(this);
        this.selectNummericThings = this.selectNummericThings.bind(this);
        this.selectCategoricThings = this.selectCategoricThings.bind(this);
        this.disableBtn = this.disableBtn.bind(this);

        this.finishChoice = this.finishChoice.bind(this);

        this.searchBookmark = this.searchBookmark.bind(this);

        this.setNumBookmarkData = this.setNumBookmarkData.bind(this);
        this.setCatBookmarkData = this.setCatBookmarkData.bind(this);
        this.disableBtnForBookmark = this.disableBtnForBookmark.bind(this);
        this.addBookmark = this.addBookmark.bind(this);

        this.clickSearchBtn = this.clickSearchBtn.bind(this);
        this.getBookmarkBtnEngKor = this.getBookmarkBtnEngKor.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.state = {
            user : user,
            userId : "",
            isClicked1 : false,
            isClicked2 : false,
            isClicked3 : false,
            isClicked4 : false,
            isClicked5 : false,
            columns1 : "",
            columns2 : "",


            selectNummeric : [],
            selectCategoric: [],
            
            selectNummericWithKor: [],
            selectCategoricWithKor: [],

            disableBtn1 : false,
            disableBtn2 : false,
            isClicked : false,
            goAddFavorites: false,
            selectedGrpid: 1,

            totalBookmark: [{
                grpid: "",
                userid: "",
                snsrid: "",
            },],

            takeNummericBookmark: [],   //즐겨찾기된 번호의 모든 목록 (snsrid, userid, grpid)(수치형)
            takeCategoricBookmark: [],
            setNumBookmarks: [], //클릭된 수치형센서 리스트
            setCatBookmarks: [],

            numBookmarkEng: [],
            numBookmarkKor: [],
            catBookmarkEng: [],
            catBookmarkKor: [],
            
            BookmarkBtn: false,
        }

    }
    componentDidMount() {
        this.getColumnForBtn();
        this.setState({
            userId: this.props.user.id
        })
    }


    getColumnForBtn(){

        vehicleStatissticsService.getColumnsForBtnNummeric()
        .then((response) => {
            this.setState({
                columns1: response.data
            })
        })
        .catch((e) => {
            console.log(e);
        })

        vehicleStatissticsService.getColumnsForBtnCategoric()
        .then((response) => {
            this.setState({
                columns2: response.data
            })
        })
        .catch((e) => {
            console.log(e);
        })



    }

    selectNummericThings(res) {         //수치형 버튼이 클릭되었을때 배열에 넣음  
        if(res[0] === 1){   //list에 더해야하는 상황
            this.setState({
                selectNummeric: [...this.state.selectNummeric, res[1]],
                selectNummericWithKor: [...this.state.selectNummericWithKor, res[2]]
            }, () => {
                this.disableBtn(this.state.selectNummeric, this.state.columns1)
            })

        } else{     //-1일 경우 선택목록에서 삭제
                
            let btnArray = this.state.selectNummeric;
            btnArray = btnArray.filter((element) => element !== res[1]);
            let btnKorArray = this.state.selectNummericWithKor;
            btnKorArray = btnKorArray.filter((element) => element !== res[2]);
            this.setState({
                selectNummeric: btnArray,
                selectNummericWithKor: btnKorArray
            }, () => {this.disableBtn(this.state.selectNummeric, this.state.columns1)})
        }

    }
    selectCategoricThings(res) {                //범주형 버튼이 클릭되었을때 배열에 넣음  
        if(res[0] === 1){   //list에 더해야하는 상황
            this.setState({
                selectCategoric: [...this.state.selectCategoric, res[1]],
                selectCategoricWithKor: [...this.state.selectCategoricWithKor, res[2]]

            }, () => {
                this.disableBtn(this.state.selectCategoric, this.state.columns2)
            })

        } else{     //-1일 경우 선택목록에서 삭제
                
            let btnArray = this.state.selectCategoric;
            btnArray = btnArray.filter((element) => element !== res[1]);
            let btnKorArray = this.state.selectCategoricWithKor;
            btnKorArray = btnKorArray.filter((element) => element !== res[2]);

            this.setState({
                selectCategoric: btnArray,
                selectCategoricWithKor: btnKorArray
            }, () => {this.disableBtn(this.state.selectCategoric, this.state.columns2)})
        }
    }
    disableBtn(selectArray, totalColumns){          //버튼을 비활성화 하기 위한 함수

        if(selectArray.length === 5){   //5개선택되어서 버튼비활
            totalColumns.forEach(element => {
                let disabledBtn = document.getElementById(element.var);
                if(!selectArray.includes(element.var)){
                    //포함하고 있지 않을때 버튼비활
                    disabledBtn.disabled = true;
                } else{
                    //포함할때 비활X
                }
            })
            alert("더 이상 선택할 수 없습니다.");
        }else{             //버튼클릭해제로 제거해야되는 상황
            totalColumns.forEach(element => {
                let abledBtn = document.getElementById(element.var);
                abledBtn.disabled = false;
            })
        }
    }

    finishChoice(){     //완료버튼 클릭시 데이터를 모달로 보냄

        if(!this.state.BookmarkBtn){
            if(this.state.selectNummeric.length > 0 && this.state.selectCategoric.length > 0){
                
                this.props.modalFunc(false);
                let param = [];
                param.push(this.state.selectNummeric);
                param.push(this.state.selectCategoric);
                param.push(this.state.selectNummericWithKor);
                param.push(this.state.selectCategoricWithKor);
                
                this.props.func(param);
            }else{
                alert("수치형, 범주형 모두 1개 이상 선택");
            }
        }else {
            if(this.state.numBookmarkEng.length > 0 && this.state.catBookmarkEng.length > 0) {
                this.props.modalFunc(false);
                let param = [];
                param.push(this.state.numBookmarkEng);
                param.push(this.state.catBookmarkEng);
                param.push(this.state.numBookmarkKor);
                param.push(this.state.catBookmarkKor);
                this.props.func(param);
            }
        }
        
    }

    getBookmarkBtnEngKor(res){
        let arrayEng = [];
        let arrayKor = [];
        if(res[0].includes('PN')){  //즐찾되어있어서 세팅되어있는 값이 수치형일때 
            arrayEng = this.state.numBookmarkEng;
            arrayEng.push(res[1]);
            arrayKor = this.state.numBookmarkKor;
            arrayKor.push(res[2]);
            this.setState({
                numBookmarkEng: arrayEng,
                numBookmarkKor: arrayKor,
            })
        }else{

            arrayEng = this.state.catBookmarkEng;
            arrayEng.push(res[1]);
            arrayKor = this.state.catBookmarkKor;
            arrayKor.push(res[2]);
            this.setState({
                catBookmarkEng: arrayEng,
                catBookmarkKor: arrayKor,
            }, () => {
                console.log(this.state.catBookmarkEng);
            })
        }
    }

    clickSearchBtn(res) {     //즐찾버튼 1~5클릭시 
        let grpId = Number(res.target.value);
        
        switch(grpId){
            case 1:
                this.setState({
                    selectedGrpid: grpId,
                    isClicked1: !this.state.isClicked1,
                    isClicked2: false,
                    isClicked3: false,
                    isClicked4: false,
                    isClicked5: false,
                    takeCategoricBookmark: [],
                    takeNummericBookmark: [],
                    setNumBookmarks: [],
                    setCatBookmarks: [],
                    numBookmarkEng: [],
                    numBookmarkKor: [],
                    catBookmarkEng: [],
                    catBookmarkKor: [],

                }, () => {
                    this.searchBookmark(grpId);
                })
                break;
            case 2:
                this.setState({
                    selectedGrpid: grpId,
                    isClicked1: false,
                    isClicked2: !this.state.isClicked2,
                    isClicked3: false,
                    isClicked4: false,
                    isClicked5: false,
                    takeCategoricBookmark: [],
                    takeNummericBookmark: [],
                    setNumBookmarks: [],
                    setCatBookmarks: [],
                    numBookmarkEng: [],
                    numBookmarkKor: [],
                    catBookmarkEng: [],
                    catBookmarkKor: [],
                }, () => {
                    this.searchBookmark(grpId);
                })
                break;
            case 3:
                this.setState({
                    selectedGrpid: grpId,
                    isClicked1: false,
                    isClicked2: false,
                    isClicked3: !this.state.isClicked3,
                    isClicked4: false,
                    isClicked5: false,
                    takeCategoricBookmark: [],
                    takeNummericBookmark: [],
                    setNumBookmarks: [],
                    setCatBookmarks: [],
                    numBookmarkEng: [],
                    numBookmarkKor: [],
                    catBookmarkEng: [],
                    catBookmarkKor: [],
                }, () => {
                    this.searchBookmark(grpId);
                })
                break;
            case 4:
                this.setState({
                    selectedGrpid: grpId,
                    isClicked1: false,
                    isClicked2: false,
                    isClicked3: false,
                    isClicked4: !this.state.isClicked4,
                    isClicked5: false,
                    takeCategoricBookmark: [],
                    takeNummericBookmark: [],
                    setNumBookmarks: [],
                    setCatBookmarks: [],
                    numBookmarkEng: [],
                    numBookmarkKor: [],
                    catBookmarkEng: [],
                    catBookmarkKor: [],
                }, () => {
                    this.searchBookmark(grpId);
                })
                break;
            default :
                this.setState({
                    selectedGrpid: grpId,
                    isClicked1: false,
                    isClicked2: false,
                    isClicked3: false,
                    isClicked4: false,
                    isClicked5: !this.state.isClicked5,
                    takeCategoricBookmark: [],
                    takeNummericBookmark: [],
                    setNumBookmarks: [],
                    setCatBookmarks: [],
                    numBookmarkEng: [],
                    numBookmarkKor: [],
                    catBookmarkEng: [],
                    catBookmarkKor: [],
                }, () => {
                    this.searchBookmark(grpId);
                })
        }
    }

    searchBookmark(grpId){   //즐겨찾기 ) 선택된 (grpid)와 아이디(userid) 값으로 가져온 ?번항목의 즐겨찾기 데이터 조회하기버튼   
        const {isClicked1, isClicked2, isClicked3, isClicked4, isClicked5} = this.state;
        if(!isClicked1 && !isClicked2 && !isClicked3 && !isClicked4 && !isClicked5) {
            this.setState({
                BookmarkBtn: false,
            });
            return;
        }else if(isClicked1 || isClicked2 || isClicked3 || isClicked4 || isClicked5){
            this.setState({
                BookmarkBtn: true,
            })
        }

        let numSnsr = [];
        let numMarkSnsrCode = [];
        let catSnsr = [];
        let catMarkSnsrCode = [];
   
        vehicleStatissticsService.searchBookmark(this.state.userId, grpId)
        .then((response) =>{
            response.data.forEach(el => {
                if(el.snsrid.includes('PN')){
                    numSnsr.push(el);
                    numMarkSnsrCode.push(el.snsrid);
                }else{
                    catSnsr.push(el);
                    catMarkSnsrCode.push(el.snsrid);
                }
            })
            this.setState({
                totalBookmark: response.data,
                takeNummericBookmark: numSnsr,
                takeCategoricBookmark: catSnsr,
                setNumBookmarks : numMarkSnsrCode,
                setCatBookmarks : catMarkSnsrCode,
            },() => {
                this.disableBtnForBookmark(this.state.setNumBookmarks, this.state.columns1);
                this.disableBtnForBookmark(this.state.setCatBookmarks, this.state.columns2);
            })
        })
        .catch((e) => {
            console.log(e);
        })
        
    }

    setNumBookmarkData(res) {       //즐겨찾기 ) 수치형데이터를 클릭시 저장하는 함수
        if(res[0] === -1){
            let btnArray = this.state.setNumBookmarks;
            btnArray = btnArray.filter((element) => element !== res[2]);
            let btnEngArray = this.state.numBookmarkEng;
            btnEngArray = btnEngArray.filter((element) => element !== res[1]);
            let btnKorArray = this.state.numBookmarkKor;
            btnKorArray = btnKorArray.filter((element) => element !== res[3]);

            this.setState({
                setNumBookmarks: btnArray,
                numBookmarkEng: btnEngArray,
                numBookmarkKor: btnKorArray,
            }, () => {this.disableBtnForBookmark(this.state.setNumBookmarks, this.state.columns1)})
        
        
        }else{
            this.setState({
                setNumBookmarks: [...this.state.setNumBookmarks, res[2]],
                numBookmarkEng: [...this.state.numBookmarkEng, res[1]],
                numBookmarkKor: [...this.state.numBookmarkKor, res[3]],
                
            }, () => {
                this.disableBtnForBookmark(this.state.setNumBookmarks, this.state.columns1);

            })
        }
    }

    setCatBookmarkData(res) {       //즐겨찾기 ) 범주형데이터를 클릭시 저장하는 함수

        if(res[0] === -1){
            let btnArray = this.state.setCatBookmarks;
            btnArray = btnArray.filter((element) => element !== res[2]);
            let btnEngArray = this.state.catBookmarkEng;
            btnEngArray = btnEngArray.filter((element) => element !== res[1]);
            let btnKorArray = this.state.catBookmarkKor;
            btnKorArray = btnKorArray.filter((element) => element !== res[3]);
            this.setState({
                setCatBookmarks: btnArray,
                catBookmarkEng: btnEngArray,
                catBookmarkKor: btnKorArray,
            }, () => {
                this.disableBtnForBookmark(this.state.setCatBookmarks, this.state.columns2);
            })
        }
        else{
            this.setState({
                setCatBookmarks: [...this.state.setCatBookmarks, res[2]],
                
                catBookmarkEng: [...this.state.catBookmarkEng, res[1]],
                catBookmarkKor: [...this.state.catBookmarkKor, res[3]],
            }, () => {
                this.disableBtnForBookmark(this.state.setCatBookmarks, this.state.columns2);
            })
        }
    }

    disableBtnForBookmark(selectArray, totalColumns){       //즐겨찾기 버튼에서 5개이상 클릭시 버튼을 비활성화하는 함수
        if(selectArray.length === 5){   //5개선택되어서 버튼비활
            totalColumns.forEach(element => {
                let disabledBtn = document.getElementById(element.code);
                if(!selectArray.includes(element.code)){
                    //포함하고 있지 않을때 버튼비활
                    disabledBtn.disabled = true;
                } else{
                    //포함할때 비활X
                }
            })
        }else{             //버튼클릭해제로 제거해야되는 상황
            totalColumns.forEach(element => {
                let abledBtn = document.getElementById(element.code);
                abledBtn.disabled = false;
            })
        }
    }
    addBookmark(){      //즐겨찾기 추가 버튼
        if(!this.state.BookmarkBtn){
            alert("즐겨찾기추가할 숫자를 클릭하세요");
            return;
        }
        if(this.state.setCatBookmarks.length === 0 && this.state.setNumBookmarks.length === 0){         //선택된 버튼이 없을때,
            alert("수치형, 범주형 모두 1개 이상 선택");


        }else if(this.state.setCatBookmarks.length > 0 
            && this.state.setNumBookmarks.length > 0){  //선택된 버튼이 있을때

                const allAddBookmarks = this.state.setNumBookmarks.concat(this.state.setCatBookmarks);
        
                const data = [];
                allAddBookmarks.forEach(el => {
                    const afterData = { grpid: this.state.selectedGrpid, snsrid: el, userid: this.state.userId };
                    data.push(afterData);
                })
                vehicleStatissticsService.insertBookmark(data)
                .then((response) => {
                })
                .catch((e) => {
                    console.log(e);
                })

            }
    }
    closeModal(){
        this.props.func(null);
        this.props.modalFunc(false);
    }
    render() {
        const {isClicked1, isClicked2, isClicked3, isClicked4, isClicked5} = this.state;
        return (
            <div className="pop02">
                <h4>차량 센서데이터 선택</h4>
                <button className="close small" onClick={this.closeModal}>
                    &times;
                </button>
                <div className="choosebox">
					<div className="choose01">
						<h5>즐겨찾기</h5>
						<button onClick={this.clickSearchBtn} value='1' style={{backgroundColor: isClicked1 ? '#4A4592' : '#fff' , color: isClicked1 ? '#fff' : '#000'}}>1</button>
						<button onClick={this.clickSearchBtn} value='2' style={{backgroundColor: isClicked2 ? '#4A4592' : '#fff' , color: isClicked2 ? '#fff' : '#000'}}>2</button>
						<button onClick={this.clickSearchBtn} value='3' style={{backgroundColor: isClicked3 ? '#4A4592' : '#fff' , color: isClicked3 ? '#fff' : '#000'}}>3</button>
						<button onClick={this.clickSearchBtn} value='4' style={{backgroundColor: isClicked4 ? '#4A4592' : '#fff' , color: isClicked4 ? '#fff' : '#000'}}>4</button>
						<button onClick={this.clickSearchBtn} value='5' style={{backgroundColor: isClicked5 ? '#4A4592' : '#fff' , color: isClicked5 ? '#fff' : '#000'}}>5</button>
						<button className="btn-se" onClick={this.addBookmark}>즐겨찾기설정</button>
					</div>
                            <div id="abc" className="choose02">
                                {
                                    this.state.BookmarkBtn
                                    ?
                                    <>
                                    
                                    <h3>수치형(5개까지만 선택가능)</h3>
                                    <div className="btn-to">
	                                    {
	                                        this.state.columns1 &&
	                                        this.state.columns1.map((element) => {
	                                            return (
	                                                <BookmarkBtn content={element} key={element.var}
	                                                bookmark={this.state.takeNummericBookmark} setBookmark={this.setNumBookmarkData} 
	                                                setBtnFunc={this.getBookmarkBtnEngKor}
	                                                name="nummeric" />
	                                            )
	                                        })
	                                    }
                                    </div>
                                    
									<h3>범주형(5개까지만 선택가능)</h3>
									<div className="btn-bo">
										{
											this.state.columns2 &&
											this.state.columns2.map((element) => {
												return (
													<BookmarkBtn content={element} key={element.var}
														bookmark={this.state.takeCategoricBookmark} setBookmark={this.setCatBookmarkData}
														setBtnFunc={this.getBookmarkBtnEngKor} name="categoric" />
												)
											})
										}
									</div>
                                    </>
                                    : 
                                    <>
                                    
									<h3>수치형(5개까지만 선택가능)</h3>
									<div className="btn-to">
										{
											this.state.columns1 &&
											this.state.columns1.map((element) => {
												return (
													<SensorBtn content={element} key={element.var} clearData={this.state.BookmarkBtn}
														btnFunc={this.selectNummericThings}
														name="nummeric" />
												)
											})
										}
									</div>
                                    
									<h3>범주형(5개까지만 선택가능)</h3>
									<div className="btn-bo">
										{
											this.state.columns2 &&
											this.state.columns2.map((element) => {
												return (
													<SensorBtn content={element} key={element.var} clearData={this.state.BookmarkBtn}
														btnFunc2={this.selectCategoricThings}
														name="categoric" />
												)
											})
										}
									</div>
                                    </>
                                }
                            </div>
                            <div className="choose03">
                                <h5>초기화</h5>
                                <button className="btn-ch" onClick={this.finishChoice} >완료</button> 
                            </div>
                </div>
            </div>
        );
    }
}
function mapStateToProps(state) {
    const { user } = state.auth;
    return {
        user,
    };
}

export default connect(mapStateToProps)(ChoiceSensorModal);
