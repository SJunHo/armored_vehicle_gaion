

import { connect } from "react-redux";
import React, { Component } from 'react';
import {ListBox} from 'primereact/listbox';
//npm install --force primereact primeicons

// import {
//     selectAllVehicle
// } from "../../actions/monitoring/tutorials";

import 'primeicons/primeicons.css';
import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.css';
import vehicleStatissticsService from "../../../services/analysis/vehicleStatics.service";
// "../../../services/monitoring/vehicleStatistics.service";

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
        this.clickYear = this.clickYear.bind(this);
        this.clickMonth = this.clickMonth.bind(this);


        this.getFileData = this.getFileData.bind(this);

        this.state = {


            allList : "",

            selectedDiv: "",
            selectedBrgd: "",
            selectedId: "",
            selectedYear: "",
            selectedMonth: "",
            selectedFile: "",

            divList : "",
            brgdList: "",
            idList: "",
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
        }


    }

    componentDidMount() {
        console.log(this.props);
        console.log(this.props.data);

        console.log(this.props.allVehicleInfo);

        this.setState({
            choiceVehicle : this.props.data
        }, () => {console.log(this.state.choiceVehicle)})



        //트리로 선택됐을때 변수 대입
        this.setState({ 
            choiceVehicle : this.props.data,
            selectedDiv : this.props.data.divs,
            selectedId : this.props.data.sdaid,
        })
        this.props.data.brgd === null
        ? this.setState({ selectedBrgd : this.props.data.bn })
        : this.setState({ selectedBrgd : this.props.data.brgd + " " + this.props.data.bn});
        
        
        console.log(this.props);
        //sda의 모든 데이터 긁어옴
        vehicleStatissticsService.getAllVehicleData()
        .then((response) => {
            console.log(response);
            this.setState({
                allList: response.data,
            }, () => {this.setItem()});
            console.log(response);
        })
        .catch((e) => {
            console.log(e);
        })
    }



    componentDidUpdate(prevProps, prevState) {

        if(prevState.allList !== this.state.allList){
            // this.setItem();
            console.log(this.state.allList);
        }
        console.log(this.props);
    }

    setItem() {
        let divarray = [];
        let brgdArray = [];
        let idArray = [];

        let brgdPlusBn = "";
        let brgd = "";
        let bn = "";
        
        console.log(this.state.allList);
        console.log(this.state.selectedDiv);
        console.log(this.state.selectedId);
        console.log(this.state.selectedId);

        if(this.state.selectedId === undefined){
            //url에 아이디값이 없을때 
            console.log("아이디값 존재 X 세팅해야됩니다.");
            console.log(this.state.allList);
            console.log(this.state.allList.array);
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
                    }else if(brgd === ""){
                        idArray.push(element.sdaid);
                    }
                }

                this.getFileData(this.state.selectedId);
            })
            console.log("map finish()~~~");
            const brgdBnSet = new Set(brgdArray);
            const uniqueArr = [...brgdBnSet];

            console.log(uniqueArr);
            this.setState({
                divList: divarray,
                brgdList: uniqueArr,
                idList : idArray,
            })
        }

        console.log(this.state.allList);
    }


    getFileData(id){
        
        let yearMonthArr = [];
        let yearArray = [];
        let monthArray = [];
        let latestYear = "";
        let latestMonth = "";

        vehicleStatissticsService.getAllFileWithId(id)
        .then((response) => {
            console.log(response.data);
            this.setState({
                file : response.data,
            });            
            console.log(this.state.file);
            this.state.file.forEach(element => {
                let yearMonth = element.operdate;
                let splitYear = yearMonth.split("-");
                yearMonthArr.push(splitYear);
                yearArray.push(splitYear[0]);
            })
            console.log(yearMonthArr);
            console.log(yearArray);

            const yearSet = new Set(yearArray);
            const yearUnqArr = [...yearSet];
            yearUnqArr.sort((a, b) => b-a);
            console.log(yearUnqArr);

            latestYear = yearArray[0];
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
            console.log(monthArray);
            const monthSet = new Set(monthArray);
            const monthUnqArr = [...monthSet];
            monthUnqArr.sort((a,b) => b-a);
            console.log(monthUnqArr);

            latestMonth = monthUnqArr[0];
            for(let i = 1; i < monthUnqArr.length; i++){
                if(latestMonth < monthUnqArr[i]){
                    latestMonth = monthUnqArr[i];
                }
            }
            console.log(latestMonth);
            
            const yearMonth = latestYear+ "-" + latestMonth;
            let fileArr = [];
            this.state.file.forEach(element => {
                if(element.operdate.includes(yearMonth)){
                    fileArr.push(element.filenm);
                }
            })
            console.log(fileArr);
            let latestFile = fileArr[0];
            let count = 0;
            for(let i = 1; i < fileArr.length; i++){

                for(let j = 0; j < fileArr[i].length; j++){
                    console.log(fileArr[i]);
                    console.log(fileArr[i][j]);

                    if(latestFile[j] > fileArr[i][j]){
                        break;
                    }else if(latestFile[j] < fileArr[i][j]){
                        latestFile = fileArr[i];
                        console.log(fileArr[i]);
                        console.log(latestFile);
                        break;
                    }else{
                        continue;
                    }
                }
                console.log(count);
                console.log(latestFile);
            }
            this.setState({
                selectedYear: latestYear,
                selectedMonth: latestMonth,
                yearList: yearUnqArr,
                monthList: monthUnqArr,
                fileList: fileArr,
                selectedFile: latestFile,

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

        console.log(this.state.allList);
        console.log(this.state.allList.array);
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
        let bn = "";
        let brgd = "";
        console.log(e);
        let value = e;

        console.log(this.state.allList);
        console.log(this.state.selectedDiv);
        console.log(this.state.selectedBrgd);
        console.log(this.state.selectedId);

        
        if(this.state.selectedBrgd === value){
            console.log("HIHIHI");
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
                    }else if(brgd === ""){
                        idArray.push(element.sdaid);
                    }
                }
            })

            this.setState({
                idList: idArray,
                selectedId : idArray[0]
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
                    }else if(brgd === ""){
                        idArray.push(element.sdaid);
                    }
                }
            })
            
            this.setState({
                idList: idArray,
                selectedBrgd: value,
                selectedId : idArray[0]
            }, () => {this.clickId(this.state.selectedId)});
        }
    }
    clickId(e){
        console.log(e);
        let value = e;
        if(value === this.state.selectedId){
            console.log(value);

            console.log("내려온 클릭");
            console.log(this.state.selectedId);
            
            this.getFileData(this.state.selectedId);

        } else{
            console.log("직접클릭");
            console.log(value);
            console.log(e.target.value);
            value = e.target.value;
            if(value === null){
                return;
            }
            console.log(value);
            this.setState({
                selectedId : value
            }, () => { this.getFileData(this.state.selectedId) });
        }
    }

    clickYear(e) {
        console.log(this.state.file);
        
        const value = e.target.value;
        console.log(value);
        if(value === null){
            return;
        }
        const operdateArr = [];
        const monthArr = [];
        this.state.file.forEach(element => {
            let operdate = element.operdate.split("-");
            console.log(operdate);
            operdateArr.push(operdate);
        })
        console.log(operdateArr);
        operdateArr.forEach(element => {
            if(value === element[0]){
                monthArr.push(element[1]);
            }
        })
        console.log(monthArr);

        const monthSet = new Set(monthArr);
        const monthUnqArr = [...monthSet];
        
        let latestMonth = monthUnqArr[0];
        for(let i = 1; i < monthUnqArr.length; i++){
            if(latestMonth < monthUnqArr[i]){
                latestMonth = monthUnqArr[i];
            }
        }

        this.setState({
            selectedYear : value,
            monthList : monthUnqArr,
            selectedMonth : latestMonth
        }, () => {this.clickMonth(this.state.selectedMonth)})
    }

    clickMonth(e){

        console.log(e);
        let value = e;

        let yearMonth = "";
        const filenmArr = [];
        if(this.state.selectedMonth === value){
            console.log("해리스트 클릭");
            yearMonth = this.state.selectedYear+"-"+this.state.selectedMonth;
            console.log(yearMonth);
            this.state.file.forEach(element => {
                if(element.operdate.includes(yearMonth)){
                    filenmArr.push(element.filenm);
                }
            })
            
            console.log(this.state.selectedFile);
            console.log(filenmArr);
            this.setState({
                selectedFile: filenmArr[0],
                fileList: filenmArr,
                selectedMonth: value,
            })

        }else{
            console.log("월 클릭");
            value = e.target.value;
            console.log(yearMonth);
            if(value === null){
                return; 
            }
            yearMonth = this.state.selectedYear+"-"+value;
            this.state.file.forEach(element => {
                if(element.operdate.includes(yearMonth)) {
                    filenmArr.push(element.filenm);
                }
            })
            console.log(this.state.selectedFile);
            console.log(filenmArr);
            this.setState({
                selectedFile: filenmArr[0],
                fileList: filenmArr,
                selectedMonth: value
            })
        }

        console.log(filenmArr);
    }
    clickFile(e){
        console.log(this.state.fileList);
        console.log(this.state.selectedFile);
        if(e.target.value === null){
            return;
        }
        this.setState({
            selectedFile: e.target.value
        })
    }
    click(){
        console.log("확인");
        console.log(this.state.selectedFile);
        if(this.state.selectedFile === ""){
            console.log("xxxxx");
            alert("파일을 선택하세요");
        }else{
            console.log("ooooo");
            console.log(this.props);
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
                <h4>choiceDate !!! </h4>
                <div id="grouplistbox" className="listbox">
                    <div className="list-top row">
                        <ListBox className="col-1" value={this.state.selectedDiv} options={this.state.divList} onChange={(e) => this.clickDiv(e)} itemTemplate={this.countryTemplate}style={{ width: '15rem' }} listStyle={{ maxHeight: '250px' }} />

                        <ListBox className="col-2" value={this.state.selectedBrgd} options={this.state.brgdList} onChange={(e) => this.clickBrgd(e)} itemTemplate={this.brgdTemplate}style={{ width: '15rem' }} listStyle={{ maxHeight: '250px' }} />

                        <ListBox className="col-2" value={this.state.selectedId} options={this.state.idList} onChange={(e) => this.clickId(e)} itemTemplate={this.idTemplate}style={{ width: '15rem' }} listStyle={{ maxHeight: '250px' }} />

                        <ListBox className="col-2" value={this.state.selectedYear} options={this.state.yearList} onChange={(e) => this.clickYear(e)} itemTemplate={this.yearTemplate} style={{ width: '15rem' }} listStyle={{ maxHeight: '250px' }} />

                        <ListBox className="col-2" value={this.state.selectedMonth} options={this.state.monthList} onChange={(e) => this.clickMonth(e)} itemTemplate={this.monthTemplate} style={{ width: '15rem' }} listStyle={{ maxHeight: '250px' }} />

                        <ListBox className="col-2" value={this.state.selectedFile} options={this.state.fileList} 
                        onChange={(e) => this.clickFile(e)}
                        itemTemplate={this.fileTemplate} style={{ width: '15rem' }} listStyle={{ maxHeight: '250px' }} />
                    </div>
                    <div className="list-bottom">
                        <button className="btn-ch" onClick={this.click} > 선택완료 </button>
                    </div>
                </div>
            </div>
        );
    }
}

// const mapStateToProps = (state) => {
//     return {
//         allVehicleInfo : state.tutorials,
//     };
// };

export default connect(null, {
    // selectAllVehicle
})(ChoiceDateModal);
