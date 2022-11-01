

import { connect } from "react-redux";
import React, { Component } from 'react';
import {ListBox} from 'primereact/listbox';
import 'primeicons/primeicons.css';
import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.css';
import vehicleStatissticsService from "../../../services/analysis/vehicleStatistics.service";

class ChoiceDateModal extends Component {
    constructor(props) {
        super(props);
        this.click = this.click.bind(this);
        this.setItem = this.setItem.bind(this);
        
        this.countryTemplate = this.countryTemplate.bind(this);
        this.brgdTemplate = this.brgdTemplate.bind(this);
        this.idTemplate = this.idTemplate.bind(this);
        this.yearTemplate = this.yearTemplate.bind(this);
        this.monthTemplate = this.monthTemplate.bind(this);
        this.fileTemplate = this.fileTemplate.bind(this);
  
        this.clickBrgd = this.clickBrgd.bind(this);
        this.clickDiv = this.clickDiv.bind(this);
        this.clickId = this.clickId.bind(this);
        this.clickName = this.clickName.bind(this);
        this.clickYear = this.clickYear.bind(this);
        this.clickMonth = this.clickMonth.bind(this);
    

        this.getFileData = this.getFileData.bind(this);

        this.state = {


            allList : "",

            selectedDiv: "",
            selectedBrgd: "",
            selectedId: "",
            selectedName: "",
            selectedYear: "",
            selectedMonth: "",
            selectedFile: "",

            divList : "",
            brgdList: "",
            idList: "",
            nameList : "",
            yearList: "",
            monthList : "",
            fileList: "",

            file: "",

            choiceVehicle : {
                sdaid : "",
                sdanm : "",
                div : "",
                brgd : "",
                bn : "",
                rgstno : "",
                sn : "",
                mfdt : "",
                rgstdt : "",
                acqdt : "",
                sdatype : "",
                crtdt : "",
                crtor : "",
                mdfcdt : "",
                mdfr : "",
                divcode : "",
                brgdbncode : "",
            },
            loading : false,
        }


    }

    componentDidMount() {

        this.setState({
            choiceVehicle : this.props.data,
        }, () => {console.log(this.state.choiceVehicle)})



        //트리로 선택됐을때 변수 대입
        this.setState({ 
            choiceVehicle : this.props.data,
            selectedDiv : this.props.data.divs,
            selectedId : this.props.data.sdaid,
            selectedName : this.props.data.sdanm,
        })
        this.props.data.brgd === null
        ? this.setState({ selectedBrgd : this.props.data.bn })
        : this.setState({ selectedBrgd : this.props.data.brgd + " " + this.props.data.bn});
        
        
        //sda의 모든 데이터 긁어옴
        vehicleStatissticsService.getAllVehicleData()
        .then((response) => {
            this.setState({
                allList: response.data,
            }, () => {this.setItem()});
        })
        .catch((e) => {
            console.log(e);
        })
    }



    componentDidUpdate(prevProps, prevState) {

    }

    setItem() {
        let divarray = [];
        let brgdArray = [];
        let idArray = [];
        let nameArray = [];
        let brgdPlusBn = "";
        let brgd = "";
        let bn = "";
        
        if(this.state.selectedId === undefined){
            //url에 아이디값이 없을때 
            this.state.allList.forEach(element => {
                if(!divarray.includes(element.divs)){
                    divarray.push(element.divs);
                }

            });
            this.setState({
                divList : divarray
            })
        }else{          //아래 map()은 초기값 세팅을 위해
            this.state.allList.forEach(element => {
                if(!divarray.includes(element.divs)){
                    divarray.push(element.divs);
                }
                if(element.divs === this.state.selectedDiv && element.brgd === null){
                    if(!brgdArray.includes(element.bn)){
                        brgdArray.push(element.bn);
                    }
                }
                if(element.divs === this.state.selectedDiv && element.brgd !== null){
                    if(!brgdArray.includes(element.brgd)){
                        brgdArray.push(element.brgd + " " + element.bn);
                    }
                }


                brgdPlusBn = this.state.selectedBrgd.split(" ");
                if(brgdPlusBn.length > 1) {
                    brgd = brgdPlusBn[0];
                    bn = brgdPlusBn[1];
                } else{
                    bn = brgdPlusBn[0];
                }
        
                if(this.state.selectedDiv === element.divs && bn === element.bn){
                    if(brgd === element.brgd){
                        idArray.push(element.sdaid);
                        nameArray.push(element.sdanm);
                    }else if(brgd === ""){
                        idArray.push(element.sdaid);
                        nameArray.push(element.sdanm);
                    }
                }
            })
            this.getFileData(this.state.selectedId);
            const brgdBnSet = new Set(brgdArray);
            const uniqueArr = [...brgdBnSet];
            this.setState({
                divList: divarray,
                brgdList: uniqueArr,
                idList : idArray,
                nameList : nameArray,
            })
        }
    }


    getFileData(id){
        this.setState({
            loading : true,
        });
        let yearMonthArr = [];
        let yearArray = [];
        let monthArray = [];
        let latestYear = "";
        let latestMonth = "";

        vehicleStatissticsService.getAllFileWithId(id)
        .then((response) => {
            this.setState({
                file : response.data,
            });            
            response.data.forEach(element => {
                let yearMonth = element.operdate;
                let splitYear = yearMonth.split("-");
                yearMonthArr.push(splitYear);
                yearArray.push(splitYear[0]);
            })

            const yearSet = new Set(yearArray);
            const yearUnqArr = [...yearSet];
            yearUnqArr.sort((a, b) => b-a);
            latestYear = yearUnqArr[0];
            for(let i = 1; i < yearUnqArr.length; i++){
                if(latestYear < yearUnqArr[i]){
                    latestYear = yearUnqArr[i];
                }
            }
            
            yearMonthArr.forEach(element => {
                if(element[0] === latestYear){
                    monthArray.push(element[1]);
                }
            })
            const monthSet = new Set(monthArray);
            const monthUnqArr = [...monthSet];
            monthUnqArr.sort((a,b) => b-a);

            latestMonth = monthUnqArr[0];
            for(let i = 1; i < monthUnqArr.length; i++){
                if(latestMonth < monthUnqArr[i]){
                    latestMonth = monthUnqArr[i];
                }
            }
            
            const yearMonth = latestYear+ "-" + latestMonth;
            let fileArr = [];
            response.data.forEach(element => {
                if(element.operdate.includes(yearMonth)){
                    fileArr.push(element.filenm);
                }
            })

            let latestFile = fileArr[0];
            for(let i = 1; i < fileArr.length; i++){

                for(let j = 0; j < fileArr[i].length; j++){

                    if(latestFile[j] > fileArr[i][j]){
                        break;
                    }else if(latestFile[j] < fileArr[i][j]){
                        latestFile = fileArr[i];
                        break;
                    }else{
                        continue;
                    }
                }
            }
            fileArr.sort((a, b) => b - a);
            this.setState({
                selectedYear: latestYear,
                selectedMonth: latestMonth,
                yearList: yearUnqArr,
                monthList: monthUnqArr,
                fileList: fileArr,
                selectedFile: latestFile,
                loading : false,
            })

        })
        .catch((e) => {
            console.log(e);
        });
    }



    clickDiv(e) {
        if(e.target.value === null) {
            return;
        }
        const value = e.target.value;
        let brgdPlusBn = [];
        let brgdArray = [];

        this.state.allList.forEach(element => {
            if(value === element.divs && element.brgd === null){
                if(!brgdArray.includes(element.bn)){
                    brgdArray.push(element.bn);
                }
            }
            if(value === element.divs && element.brgd !== null){
                if(!brgdArray.includes(element.brgd)){
                    brgdArray.push(element.brgd + " " + element.bn);
                }
            }
        })
        const brgdBnSet = new Set(brgdArray);
        const uniqueArr = [...brgdBnSet];
        
        brgdPlusBn = uniqueArr[0].split(" ");

        this.setState({
            selectedDiv : value,
            selectedBrgd : brgdArray[0],
            brgdList: uniqueArr,

        }, () => {this.clickBrgd(this.state.selectedBrgd)})
    }

    clickBrgd(e) {
        let brgdPlusBn = [];
        let idArray = [];
        let nameArray = [];
        let bn = "";
        let brgd = "";
        let value = e;
        
        if(this.state.selectedBrgd === value){
            brgdPlusBn = this.state.selectedBrgd.split(" ");
            if(brgdPlusBn.length > 1){
                brgd = brgdPlusBn[0];
                bn = brgdPlusBn[1];
            }else{
                bn = brgdPlusBn[0];
            }
            this.state.allList.forEach(element => {
                if(this.state.selectedDiv === element.divs && bn === element.bn){
                    if(brgd === element.brgd){
                        idArray.push(element.sdaid);
                        nameArray.push(element.sdanm);
                    }else if(brgd === ""){
                        idArray.push(element.sdaid);
                        nameArray.push(element.sdanm);
                    }
                }
            })

            this.setState({
                idList: idArray,
                nameList: nameArray,
                selectedId : idArray[0],
                selectedName : nameArray[0]
            }, () => {this.clickId(this.state.selectedId)})
            return;

        }else{
            if(e.target.value === null){
                return;
            }
            value = e.target.value;
            brgdPlusBn = value.split(" ");
            if(brgdPlusBn.length > 1){
                brgd = brgdPlusBn[0];
                bn = brgdPlusBn[1];
            }else{
                bn = brgdPlusBn[0];
            }
            this.state.allList.forEach(element => {
                if(this.state.selectedDiv === element.divs && bn === element.bn){
                    if(brgd === element.brgd){
                        idArray.push(element.sdaid);
                        nameArray.push(element.sdanm);
                    }else if(brgd === ""){
                        idArray.push(element.sdaid);
                        nameArray.push(element.sdanm);
                    }
                }
            })
            
            this.setState({
                idList: idArray,
                nameList: nameArray,
                selectedBrgd: value,
                selectedId : idArray[0],
                selectedName : nameArray[0],
            }, () => {this.clickId(this.state.selectedId)});
        }
    }

    clickName(e){
        let value = this.state.idList.at(this.state.nameList.indexOf(e.target.value));
        this.setState({
            selectedName : e.target.value
        },()=>{this.clickId(value)});
    }

    clickId(e){

        let value = e;
        if(value === this.state.selectedId){
            this.getFileData(this.state.selectedId);
        } else{
            value = e;
            if(value === null){
                return;
            }
            this.setState({
                selectedId : value, 
            }, () => { this.getFileData(this.state.selectedId) });
        }
    }

    clickYear(e) {
        const value = e.target.value;
        if(value === null){
            return;
        }
        const operdateArr = [];
        const monthArr = [];
        this.state.file.forEach(element => {
            let operdate = element.operdate.split("-");
            operdateArr.push(operdate);
        })
        operdateArr.forEach(element => {
            if(value === element[0]){
                monthArr.push(element[1]);
            }
        })
        const monthSet = new Set(monthArr);
        const monthUnqArr = [...monthSet];
        monthUnqArr.sort((a,b) => b-a);

        let latestMonth = monthUnqArr[0];
        for(let i = 1; i < monthUnqArr.length; i++){
            if(latestMonth < monthUnqArr[i]){
                latestMonth = monthUnqArr[i];
            }
        }

        this.setState({
            selectedYear : value,
            monthList : monthUnqArr,
            selectedMonth : latestMonth,
        }, () => {this.clickMonth(this.state.selectedMonth)})
    }

    clickMonth(e){

        let value = e;
        let yearMonth = "";
        const filenmArr = [];
        if(this.state.selectedMonth === value){
            yearMonth = this.state.selectedYear+"-"+this.state.selectedMonth;
            this.state.file.forEach(element => {
                if(element.operdate.includes(yearMonth)){
                    filenmArr.push(element.filenm);
                }
            })
            
            filenmArr.sort((a, b) => b - a);
            this.setState({
                selectedFile: filenmArr[0],
                fileList: filenmArr,
                selectedMonth: value,
            })

        }else{
            value = e.target.value;
            if(value === null){
                return; 
            }
            yearMonth = this.state.selectedYear+"-"+value;
            this.state.file.forEach(element => {
                if(element.operdate.includes(yearMonth)) {
                    filenmArr.push(element.filenm);
                }
            })
            filenmArr.sort((a, b) => b - a);
            this.setState({
                selectedFile: filenmArr[0],
                fileList: filenmArr,
                selectedMonth: value,
            })
        }
    }
    clickFile(e){
        if(e.target.value === null){
            return;
        }
        this.setState({
            selectedFile: e.target.value
        })
    }
    click(){

        console.log(this.state.selectedFile);
        
        if(this.state.selectedFile === "" || this.state.selectedFile === undefined){
            alert("파일을 선택하세요");
        }else{
            let param = [];
            param.push(this.state.selectedId);
            param.push(this.state.selectedFile);
            this.props.func(param);
            
            this.props.modalFunc(false);
        }

    }



    countryTemplate(option) {   
        return (
            <div className="Listitem">
                <div>{option}</div>
            </div>
        );

    }
    brgdTemplate(option) {
        return (
            <div className="Listitem">
                <div>{option}</div>
            </div>
        );
    }
    idTemplate(option) {
        return (
            <div className="Listitem">
                <div>{option}</div>
            </div>
        );
    }

    yearTemplate(option) {
        return (
            <div className="Listitem">
                <div>{option}</div>
            </div>
        );
    }

    monthTemplate(option){
        return (
            <div className="Listitem">
                <div>{option}</div>
            </div>
        );
    }
    fileTemplate(option){
        return (
            <div className="Listitem">
                <div>{option}</div>
            </div>
        );
    }

    render() {
        return (
            <div className="pop01">
                <h4>정보기준일 설정</h4>
                <div id="grouplistbox" className="listbox">
                    <div className="list-top">
                        <div className="list-top-box">
                            <p>사단 선택</p>
                            <ListBox value={this.state.selectedDiv} options={this.state.divList} onChange={(e) => this.clickDiv(e)} itemTemplate={this.countryTemplate} disabled={this.state.loading}/>
                        </div>
                        <div className="list-top-box">
                            <p>연대 선택</p>
                            <ListBox value={this.state.selectedBrgd} options={this.state.brgdList} onChange={(e) => this.clickBrgd(e)} itemTemplate={this.brgdTemplate} disabled={this.state.loading}/>
                        </div>
                        <div className="list-top-box">
                            <p>차량 선택</p>
                            <ListBox value={this.state.selectedName} options={this.state.nameList} onChange={(e) => this.clickName(e)} itemTemplate={this.idTemplate} disabled={this.state.loading}/>
                        </div>
                        <div className="list-top-box">
                            <p>연도 선택</p>
                            <ListBox value={this.state.selectedYear} options={this.state.yearList} onChange={(e) => this.clickYear(e)} itemTemplate={this.yearTemplate}/>
                        </div>
                        <div className="list-top-box">
                            <p>월 선택</p>
                            <ListBox value={this.state.selectedMonth} options={this.state.monthList} onChange={(e) => this.clickMonth(e)} itemTemplate={this.monthTemplate}/>
                        </div>
                        <div className="list-top-box">
                            <p>파일 선택</p>
                            <ListBox value={this.state.selectedFile} options={this.state.fileList} 
                            onChange={(e) => this.clickFile(e)}
                            itemTemplate={this.fileTemplate} style={{ width: '15rem' }} listStyle={{ maxHeight: '250px' }}/>
                        </div>
                       
                    </div>
                    <div className="list-bottom">
                        <button className="btn-ch" onClick={this.click} > 선택완료 </button>
                    </div>
                </div>
            </div>
        );
    }
}

export default connect(null, {
})(ChoiceDateModal);
