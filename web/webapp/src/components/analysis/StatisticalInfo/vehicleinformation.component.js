import React, { Component} from "react";
import { connect } from "react-redux";
import {
  selectEachInfo,
} from "../../../actions/analysis/tutorials";
import vehicleStatistics from "../../../services/analysis/vehicleStatistics.service";

import Chart from 'chart.js/auto';
//npm install chart.js
import { GaugeChart } from "@toast-ui/chart";
//npm install @toast-ui/chart
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Row, Col } from "react-bootstrap";
import OpenModal from "./modal.component";

import Categoric from "./categoricDistinction.component";
import "./searchEachInfo.css";

class searchEachInfo extends Component {
  constructor(props) {
    super(props);

    this.liveChartRef = React.createRef();


    this.drawingChart = this.drawingChart.bind(this);
    this.stopChart = this.stopChart.bind(this);
    this.liveStream = this.liveStream.bind(this);
    this.rangeSliderPlus = this.rangeSliderPlus.bind(this);
    this.restart = this.restart.bind(this);
    this.reverse = this.reverse.bind(this);
    
    this.openModal = this.openModal.bind(this);
    this.openModal2 = this.openModal2.bind(this);
    this.changeModal = this.changeModal.bind(this);
    this.changeModal2 = this.changeModal2.bind(this);
    this.forInputFileName = this.forInputFileName.bind(this);
    this.forUseSensor = this.forUseSensor.bind(this);

    this.drawExtraLiveChart = this.drawExtraLiveChart.bind(this);
    this.vehicleStatInfo = this.vehicleStatInfo.bind(this);
    this.progressBar =this.progressBar.bind(this);
    this.setChartData = this.setChartData.bind(this);

    this.categoricLabel = this.categoricLabel.bind(this);
    this.destroyChart = this.destroyChart.bind(this);

    this.categoricSensorIsChange = this.categoricSensorIsChange.bind(this);
    this.slowChart = this.slowChart.bind(this);
    this.fastChart = this.fastChart.bind(this);
    this.defaultBookmark = this.defaultBookmark.bind(this);

    this.state = {
      isStart: true,      //스탑버튼클릭시 체크하기위한
      setData: false,     //data가 들어오면 실행위한
      allStop: false,        //그래프다시그리기 위한
      resetChart: false,     //차트가 끝나면 다시시작위한
      loading:false,
      setModal: false,  //모달창 생성위한
      setModal2: false,
      isBack: false,
      fileNameAndId : [],
      // nummericSensor: ["RETDCHO","VOLTAGE","_2AVGSPEED","RETDTOK","REQRETDTOK"],
      // categoricSensor: ["ENGHEAT","COOLLANT","ENGOVERCTLMD","_1LOCK","_2LOCK"],
      // nummericSensorWithKor : ['리타더선택', '전압', '2축 평균속도', '리타더 토크', '요구리타더토크'],
      // categoricSensorWithKor: ['엔진예열','냉각수량','엔진오버라이드제어모드','1축 차동잠금', '2축 차동잠금'],
      nummericSensor: [],
      categoricSensor: [],
      nummericSensorWithKor : [],
      categoricSensorWithKor: [],

      i: 0,
      xData : "",
      yData : 0,
      yData0 : 0,
      yData1 : 0,
      yData2 : 0,
      yData3 : 0,

      value: 0,
      j: 0,
      forChartData : [],
      changeSensorForChart: false,      
      chartSpeed: 100,
    };
  }

  componentDidMount() {
    //this.props.selectEachInfo(this.props.match.params.id);

    this.defaultBookmark(); 
    this.interval = setInterval(this.liveStream, 100);
 
    if(this.props.match.params.id === undefined){   //통계화면이 아닌 직접 차량정보조회버튼을 클릭해서 ID가 넘어오지 않을때, 바로 팝업으로 선택하게 하기 위함
      this.setState({
        setModal : true,
        loading : true,
      }, () => {
        return(
          <OpenModal name={this.props.eachInfo.sdaid} modalDiv="choiceDate" data={this.props.eachInfo} modalFunc={this.changeModal} fileIdFunc={this.forInputFileName} />
        );
      })
    }else{      //통계화면에서 넘어온 경우 최근파일이름위함
      this.setState({
        loading : true,
      });
      this.props.selectEachInfo(this.props.match.params.id);
      vehicleStatistics.searchRecentFile(this.props.match.params.id)
      .then((response) => {
        let filenm = String(response.data);
        let filenameAddId = [this.props.match.params.id , filenm];
        this.setState({
          fileNameAndId : [...filenameAddId],
          i: 0,
          resetChart: true,
          isStart: false,
        },() => {
          if(this.state.fileNameAndId.length > 1){
            this.setChartData();
          }
        })
      })
      .catch((e) => {
        //console.log(e);
      })
    }

    const liveChart = this.liveChartRef.current.getContext('2d');
    this.myLiveChart = new Chart(liveChart, {
      type: 'line',
      data: {
        labels: [],
        datasets: [{
          pointRadius: 3,
          label: "차륜형장갑차",
          data: [],
        }],
      },
      options: {
        animation: {
          duration: 0
        },
        responsive: false,
        title:{
          fontSize:12,
          fontColor:"#000000",
        },
      }
    });

    const el = document.getElementById('gaugeChart');
    this.gaugeChart1 = new GaugeChart({ el, 
    data: {
      series: [{
        name: "",
        data: [0],
      },],
    },
    options: {
      tooltip:{
        template:false,
        visible:false
      },
      responsive:{
        animation: false
      },
      exportMenu: {
        visible: false,
      },
      chart: { 
        width: 700, 
        height: 700,
      },
      circularAxis: { scale: { min: 0, max: 5000 } },
      series: {
        selectable: false,
        solid: true,
        dataLabels: { visible: true, offsetY: -30, formatter: (value) => `${value} RPM` },
        angleRange: { start: 225, end: 135 }
      },
      theme: {
        chart: {
          backgroundColor: "",
          color:'#fff',
        },
        circularAxis: {
          lineWidth: 0,
          strokeStyle: 'rgba(0, 0, 0, 0)',
          tick: {
            lineWidth: 0,
            strokeStyle: 'rgba(0, 0, 0, 0)',
          },
          label: {
            color: 'rgba(0, 0, 0, 0)',
          },
        },
        series: {
          colors:['#3856D1'],
          solid:{
            backgroundSector:{color:'rgba(0, 0, 0, 0.1)'}
          },
          dataLabels: {
            fontSize: 65,
            fontFamily: 'Noto Sans KR',
            fontWeight: 600,
            color: '#fff',
            textBubble: {
              visible: false,
            },
          },
        },
      },
    }
    });
    this.gaugeChart1.hideTooltip();
    this.gaugeChart2 = new GaugeChart({ el, 
      data: {
        series: [{
          name: "",
          data: [0],
        },],
      },
      options: {
        tooltip:{
          template:false,
          visible:false
        },
        responsive:{
          animation: false
        },
        exportMenu: {
          visible: false,
        },
        
        chart: { width: 700, height: 700 },
        circularAxis: { scale: { min: 0, max: 90 } },
        series: {
          solid: true,
          dataLabels: { visible: true, offsetY: -30, formatter: (value) => `${value}km/h` },
          angleRange: { start: 225, end: 135 }
        },
        theme: {
          solid:{
            lineWidth:5,
          },
          chart: {
            backgroundColor: "",
          },
          circularAxis: {
            lineWidth: 0,
            strokeStyle: 'rgba(0, 0, 0, 0)',
            tick: {
              lineWidth: 0,
              strokeStyle: 'rgba(0, 0, 0, 0)',
            },
            label: {
              color: 'rgba(0, 0, 0, 0)',
            },
          },
          series: {
            colors:['#FF9B05'],
            solid:{
              backgroundSector:{color:'rgba(0, 0, 0, 0.1)'}
            },
            dataLabels: {
              fontSize: 65,
              fontFamily: 'Noto Sans KR',
              fontWeight: 600,
              color: '#fff',
              textBubble: {
                visible: false,
              },
            },
          },
        },
      }
    });
    this.gaugeChart2.hideTooltip();
  }


  componentDidUpdate(prevProps, prevState) {
    if(prevState.categoricSensor !== this.state.categoricSensor){
      this.categoricSensorIsChange();
      this.setState({
        changeSensorForChart : false,
      })
    }
  }
  componentWillUnmount() {
    clearInterval(this.interval);
  }

  defaultBookmark() {       //화면 최초 랜더링시, 센서데이터 자동으로 즐겨찾기된 버튼으로 선택위함
    let pnEng = [];
    let pnKor = [];
    let pcEng = [];
    let pcKor = [];

    vehicleStatistics.searchAllBookmarkForDefault(this.props.user.id)
    .then((response) => {
      if(response.data.length > 0){
        response.data.forEach((el, idx) => {
          if(el.code.includes('PN')){
            pnEng.push(el.var);
            pnKor.push(el.expln);
          }else{
            pcEng.push(el.var);
            pcKor.push(el.expln);
          }
        })
        this.setState({
          nummericSensor: [...pnEng],
          nummericSensorWithKor: [...pnKor],
          categoricSensor: [...pcEng],
          categoricSensorWithKor: [...pcKor],      
          i: 0,
          resetChart: true,
          changeSensorForChart: true,
        }, () => {

          this.myLiveChart.destroy();
          this.drawExtraLiveChart();
          this.drawingChart();
    
          if(this.state.fileNameAndId.length > 0 
            && this.state.nummericSensor.length > 0 
            && this.state.categoricSensor.length > 0){
              this.setChartData();
          }
        })
      }else{
        this.setState({
          nummericSensor: ['VOLTAGE', 'FRTBREAKPRS', 'BACKBREAKPRS', 'LOWOILPRS', 'LOWOILTEMP'],
          nummericSensorWithKor: ['전압', '전방제동공압', '후방제동공압', '저유기 유압유 압력', '저유기 유압유 온도'],
          categoricSensor: ['ENGWARNING', 'ENGGOV', 'ENGOILSTA', 'ENGHEAT', 'COOLLANT'],
          categoricSensorWithKor: ['엔진경고', '엔진거버너', '엔진오일압력상태', '엔진예열', '냉각수량'],      
          i: 0,
          resetChart: true,
          changeSensorForChart: true,
        }, () => {

          this.myLiveChart.destroy();
          this.drawExtraLiveChart();
          this.drawingChart();
    
          if(this.state.fileNameAndId.length > 0 
            && this.state.nummericSensor.length > 0 
            && this.state.categoricSensor.length > 0){
              this.setChartData();
          }
        })
      }
    })
    .catch((e) => {
      console.log(e);
    })
  }
  //0.1초마다 실행되는 함수
  liveStream(){     // interval()

    let chartData = [];
    chartData = this.state.forChartData;
    if(this.state.isStart && !this.state.isBack){     //재생상태일때

      if(this.state.i === chartData.length){    //슬라이더바가 끝에 도달시, ( 그래프가 끝났을 때 )
        
        const changeText = document.getElementById('stopNStart');
        changeText.innerText = "▶";
        
        this.setState({ 
          isStart: false
        });
        
      }else {         //슬라이더바가 끝이 아닐때 ( 그래프 재생가능할 떄 ) 업데이트.
        this.categoricLabel();
        this.rangeSliderPlus();
        let xData = this.state.forChartData[this.state.i].DTTIME.split(' ')[1];
        let yData = this.state.forChartData[this.state.i][this.state.nummericSensor[0]];
        let yData1 = this.state.forChartData[this.state.i][this.state.nummericSensor[1]];
        let yData2 = this.state.forChartData[this.state.i][this.state.nummericSensor[2]];
        let yData3 = this.state.forChartData[this.state.i][this.state.nummericSensor[3]];
        let yData4 = this.state.forChartData[this.state.i][this.state.nummericSensor[4]];

        this.state.nummericSensor.forEach((el, idx) => {
          if(idx === 0){   //기존그래프에 그려지는 index 0 은 패스
          }else{
            switch(idx){    //데이터 50개 넘어가면 쉬프트를 위함
              case 1:        
                if(this.liveChart0.data.datasets[0].data.length >= 50){
                  this.liveChart0.data.labels.shift();
                  this.liveChart0.data.datasets[0].data.shift();
                }
                this.liveChart0.data.labels.push(xData);
                this.liveChart0.data.datasets[0].data.push(yData1);
                this.liveChart0.update();
                break;
              
              case 2:
                if(this.liveChart1.data.datasets[0].data.length >= 50){
                  this.liveChart1.data.labels.shift();
                  this.liveChart1.data.datasets[0].data.shift();
                }
                this.liveChart1.data.labels.push(xData);
                this.liveChart1.data.datasets[0].data.push(yData2);
                this.liveChart1.update();
                break;

              case 3:
                if(this.liveChart2.data.datasets[0].data.length >= 50){
                  this.liveChart2.data.labels.shift();
                  this.liveChart2.data.datasets[0].data.shift();
                }
                this.liveChart2.data.labels.push(xData);
                this.liveChart2.data.datasets[0].data.push(yData3);
                this.liveChart2.update();
                break;
              
              default :
              if(this.liveChart3.data.datasets[0].data.length >= 50){
                this.liveChart3.data.labels.shift();
                this.liveChart3.data.datasets[0].data.shift();
              }
                this.liveChart3.data.labels.push(xData);
                this.liveChart3.data.datasets[0].data.push(yData4);
                this.liveChart3.update();
                break;
            }
          }
        })

        if(this.myLiveChart.data.datasets[0].data.length >= 50){
          this.myLiveChart.data.labels.shift();
          this.myLiveChart.data.datasets[0].data.shift();
        }
        
        this.myLiveChart.data.labels.push(xData);
        this.myLiveChart.data.datasets[0].data.push(yData);
        this.myLiveChart.update();
        
        this.gaugeChart1.setData({
          categories:[],
          series: [{
            name: 'new',
            data: [this.state.forChartData[this.state.i].ENGSPD],
          }]
        });
        this.gaugeChart2.setData({
          categories:[],
          series:[{
            name: 'hello',
            data: [this.state.forChartData[this.state.i].SDHSPD],
          }]
        });
  
        this.setState({i: this.state.i + 1});
      }

    } else if(this.state.isBack && this.state.isStart){       //역재생 상태일 떄
      if(this.state.i === 0){    //슬라이더바가 끝에 도달시, ( 그래프가 끝났을 때 )
        
        const changeText = document.getElementById('stopNStart');
        changeText.innerText = "▶";
        
        this.setState({ 
          isStart: false,
          isBack: false
        });
        
      }else {         //슬라이더바가 끝이 아닐때 ( 그래프 재생가능할 떄 ) 업데이트.
        this.categoricLabel();
        this.rangeSliderPlus();
        let xData = this.state.forChartData[this.state.i].DTTIME.split(' ')[1];
        let yData = this.state.forChartData[this.state.i][this.state.nummericSensor[0]];
        let yData1 = this.state.forChartData[this.state.i][this.state.nummericSensor[1]];
        let yData2 = this.state.forChartData[this.state.i][this.state.nummericSensor[2]];
        let yData3 = this.state.forChartData[this.state.i][this.state.nummericSensor[3]];
        let yData4 = this.state.forChartData[this.state.i][this.state.nummericSensor[4]];

        this.state.nummericSensor.forEach((el, idx) => {
          if(idx === 0){   //기존그래프에 그려지는 index 0 은 패스
          }else{
            switch(idx){    //데이터 50개 넘어가면 쉬프트를 위함
              case 1:        
                if(this.liveChart0.data.datasets[0].data.length >= 50){
                  this.liveChart0.data.labels.pop();
                  this.liveChart0.data.datasets[0].data.pop();
                }
                this.liveChart0.data.labels.unshift(xData);
                this.liveChart0.data.datasets[0].data.unshift(yData1);
                this.liveChart0.update();
                break;
              
              case 2:
                if(this.liveChart1.data.datasets[0].data.length >= 50){
                  this.liveChart1.data.labels.pop();
                  this.liveChart1.data.datasets[0].data.pop();
                }
                this.liveChart1.data.labels.unshift(xData);
                this.liveChart1.data.datasets[0].data.unshift(yData2);
                this.liveChart1.update();
                break;

              case 3:
                if(this.liveChart2.data.datasets[0].data.length >= 50){
                  this.liveChart2.data.labels.pop();
                  this.liveChart2.data.datasets[0].data.pop();
                }
                this.liveChart2.data.labels.unshift(xData);
                this.liveChart2.data.datasets[0].data.unshift(yData3);
                this.liveChart2.update();
                break;
              
              default :
              if(this.liveChart3.data.datasets[0].data.length >= 50){
                this.liveChart3.data.labels.pop();
                this.liveChart3.data.datasets[0].data.pop();
              }
                this.liveChart3.data.labels.unshift(xData);
                this.liveChart3.data.datasets[0].data.unshift(yData4);
                this.liveChart3.update();
                break;
            }
          }
        })

        if(this.myLiveChart.data.datasets[0].data.length >= 50){
          this.myLiveChart.data.labels.pop();
          this.myLiveChart.data.datasets[0].data.pop();
        }
        
        this.myLiveChart.data.labels.unshift(xData);
        this.myLiveChart.data.datasets[0].data.unshift(yData);
        this.myLiveChart.update();
        
        this.gaugeChart1.setData({
          categories:[],
          series: [{
            name: 'new',
            data: [this.state.forChartData[this.state.i].ENGSPD],
          }]
        });
        this.gaugeChart2.setData({
          categories:[],
          series:[{
            name: 'hello',
            data: [this.state.forChartData[this.state.i].SDHSPD],
          }]
        });
  
        this.setState({i: this.state.i - 1});
      }
    }else { // 정지 상태일 떄
      // console.log("정지버튼 눌러져있음 = isStart = false");
    }
  }

  stopChart() {   //그래프의 정지버튼 재생상태를 컨트롤
    
    const changeText = document.getElementById('stopNStart');
    let sliderValue = document.getElementById('slider');
    // sliderValue = Number(sliderValue.value) + 1;
    sliderValue = Number(sliderValue.value);
    if(this.state.forChartData.length === this.state.i 
      && this.state.forChartData.length > 0){  //데이터세팅이 되고 슬라이더바상태가 100일때

      if(this.state.forChartData.length === sliderValue){ //슬라이더바는 끝났을때 드래그없이 재생버튼눌렀을때
        this.setState({
          i: 0,
          resetChart: true,
        });

      }else{    //슬라이더바는 100이나 드래그로 슬라이더바의 값을 수정할때
        this.setState({
          i: Number(sliderValue)
        },() => {
          this.myLiveChart.destroy();
          this.destroyChart();
          this.drawExtraLiveChart();
          this.drawingChart();
        });
      }
    }
    if(this.state.i === 0 && this.state.forChartData.length > 0){    //슬라이더바가 0이고 값이 세팅 되었을때
      if(this.state.changeSensorForChart){      //센서가 바뀌었을때
        this.setState({
          // i: Number(sliderValue)
          i: 0,
          resetChart: true,
        }, () => {
          this.myLiveChart.destroy();
          this.destroyChart();
          this.drawExtraLiveChart();
          this.drawingChart();
        });
      }else{      //센서가 바뀌지 않았을때
        this.setState({
          i: Number(sliderValue)
        });
      }
      
    }
    //공통 실행
    if(this.state.fileNameAndId.length > 0 
      && this.state.nummericSensor.length > 0 
      && this.state.categoricSensor.length > 0){
      if(!this.state.isStart){    //정지상태 => 재생
        changeText.innerText = "][";
        this.setState({ 
          isStart: true,
          isBack : false,
        });
      }else{      //재생상태 ==> 정지
        changeText.innerText = "▶";
        this.setState({ 
          isStart: false,
          //resetChart: false,
          isBack : false,
        });
      }
    } else{
      alert("파일, 센서정보를 선택");
      this.setState({
        i: 0,
        resetChart: true,
        isBack : false,
      })
    }
  }

  restart(){      //리셋버튼
    const changeText = document.getElementById('stopNStart');
    changeText.innerText = "▶";

    this.setState({ 
      i : 0,
      resetChart : true,
      isStart : false,
      isBack : false,
    } , () => {
      this.rangeSliderPlus();
    });      
  }

  reverse(){  // 역재생
    const changeText = document.getElementById('stopNStart');
    changeText.innerText = "][";
    this.setState({
      isStart : true,
      isBack : true,
    });
  }

  rangeSliderPlus() {   //슬라이더바값을 세팅하고 그래프를 삭제한후 그래프 그려주는 함수 호출

    let chartData = [];
    chartData = this.state.forChartData;
    const rangeSlider = document.getElementById('slider');

    if(this.state.resetChart){    //리셋버튼 눌렀을때.  (그래프를 삭제하고 그래프를 다시그린다(하나의 캔버스에 여러개의 그래프를 그릴 수 없어))
      rangeSlider.value = 0;
      rangeSlider.max = chartData.length - 1;
      this.myLiveChart.destroy();
      this.destroyChart();

      this.gaugeChart1.setData({
        categories:[],
        series: [{
          name: 'new',
          data: [0],
        }]
      });
      this.gaugeChart2.setData({
        categories:[],
        series: [{
          name: 'Hello',
          data: [0]
        }]
      });
      this.drawExtraLiveChart();   //destroy한 차트 다시 그리기 위한 함수실행
      this.drawingChart();

    } else{   //리셋상태가 아닐때
      
      if(this.state.i === 0){   //슬라이더바를ㄹ 처음으로 돌리면 0이 아니라 1부터 시작하기때문에 0으로 설정해준다.
        rangeSlider.value = 0;
      }else if(this.state.isBack){
        rangeSlider.value = Number(rangeSlider.value) - 1;
      }else{      //일반재생상태로 슬라이더바의 값을 증가시키기 위함
        rangeSlider.value = Number(rangeSlider.value) + 1;
      }
      rangeSlider.max = chartData.length -1;

      if(this.state.i !== Number(rangeSlider.value) && !this.state.isBack){   //드래그로 슬라이더바를 움직였을때. 
        this.setState({ 
          i: Number(rangeSlider.value),
        });

        this.myLiveChart.destroy();
        this.destroyChart();
        this.drawExtraLiveChart();
        this.drawingChart();
      }
    }
  }

  drawingChart() {
    const liveChart = this.liveChartRef.current.getContext('2d');
    this.myLiveChart = new Chart(liveChart, {
      type: 'line',
      data: {
        labels: [],
        datasets: [{
          pointRadius: 3,
          pointBackgroundColor:['rgba(125, 136, 253, 1)'],
          pointBorderColor:['rgba(125, 136, 253, 1)'],
          backgroundColor:'rgba(125, 136, 253, 1)',
          borderColor: 'rgba(125, 136, 253, 1)',
          label: this.state.nummericSensorWithKor[0],
          data: [],
        }],
      },
      options: {
        maintainAspectRatio :false,
        plugins: {
          legend: {
            display:false
          },
          title:{
            display:true,
            text: this.state.nummericSensorWithKor[0],
            padding: {
                top: 15,
                bottom: 30
            },
            color:'#ffffff',
            font:{
              family:'Noto Sans KR',
              size:16,
              weight:400
            },
            position:'bottom'
          }
        },
        animation:{
          duration: 0
        },
        responsive: false,
        scales: {
          y:{
            ticks: {color: "white", beginAtZero: true},
          },
          x: {
            ticks: {color: "white", beginAtZero: true}
          },
        }
      }
    });
    this.setState({ resetChart : false});

  }

  drawExtraLiveChart(){ //추가그래프그려주는 함수
    this.state.nummericSensor.forEach((element, idx) => {
      if(idx === 0){
        //0일때는 그려져있는 차트에 그리기 떄문에 빼고 진행
      } else{
        let id = 'liveChart'+idx;
        let chart = document.getElementById(id).getContext('2d');
        switch(idx){
          case 1:
            this.liveChart0 = new Chart(chart, {
              type: 'line',
              data: {
                labels: [],
                datasets: [{
                  pointRadius: 3,
                  pointBackgroundColor:['rgba(125, 136, 253, 1)'],
                  pointBorderColor:['rgba(125, 136, 253, 1)'],
                  backgroundColor:'rgba(125, 136, 253, 1)',
                  borderColor: 'rgba(125, 136, 253, 1)',
                  label: this.state.nummericSensorWithKor[idx],
                  data: [],
                }],
              },
              options: {
                maintainAspectRatio :false,
                plugins: {
                  legend: {
                    display:false
                  },
                  title:{
                    display:true,
                    text: this.state.nummericSensorWithKor[idx],
                        padding: {
                          top: 15,
                          bottom: 30
                        },
                        color:'#ffffff',
                        font:{
                          family:'Noto Sans KR',
                          size:16,
                          weight:400
                        },
                        position:'bottom'
                  }
                },
                animation: {
                  duration: 0
                },
                responsive: false,
                title:{
                  fontSize:12,
                  fontColor:"#ffffff",
                },
                scales: {
                  y:{
                    ticks: {color: "white", beginAtZero: true}
                  },
                  x: {
                    ticks: {color: "white", beginAtZero: true}
                  }
                },
              }
            });
            
            break;

          case 2:
            this.liveChart1 = new Chart(chart, {
              type: 'line',
              data: {
                labels: [] ,
                datasets: [{
                  pointRadius: 3,
                  pointBackgroundColor:['rgba(125, 136, 253, 1)'],
                  pointBorderColor:['rgba(125, 136, 253, 1)'],
                  backgroundColor:'rgba(125, 136, 253, 1)',
                  borderColor: 'rgba(125, 136, 253, 1)',
                  label: this.state.nummericSensorWithKor[idx],
                  data: [],
                }],
              },
              options: {
                maintainAspectRatio :false,
                plugins: {
                  legend: {
                    display:false
                  },
                  title:{
                    display:true,
                    text: this.state.nummericSensorWithKor[idx],
                    padding: {
                      top: 15,
                      bottom: 30
                    },
                    color:'#ffffff',
                    font:{
                      family:'Noto Sans KR',
                      size:16,
                      weight:400
                    },
                    position:'bottom'
                  }
                },
                animation: {
                  duration: 0
                },
                responsive: false,
                title:{
                  fontSize:12,
                  fontColor:"#000000",
                },
                scales: {
                  y:{
                    ticks: {color: "white", beginAtZero: true}
                  },
                  x: {
                    ticks: {color: "white", beginAtZero: true}
                  }
                }
              }
            });

            break;
          
          case 3:
            this.liveChart2 = new Chart(chart, {
              type: 'line',
              data: {
                labels: [],
                datasets: [{
                  pointRadius: 3,
                  pointBackgroundColor:['rgba(125, 136, 253, 1)'],
                  pointBorderColor:['rgba(125, 136, 253, 1)'],
                  backgroundColor:'rgba(125, 136, 253, 1)',
                  borderColor: 'rgba(125, 136, 253, 1)',
                  label: this.state.nummericSensorWithKor[idx],
                  data: [],
                }],
              },
              options: {
                maintainAspectRatio :false,
                plugins: {
                  legend: {
                    display:false
                  },
                  title:{
                    display:true,
                    text: this.state.nummericSensorWithKor[idx],
                    padding: {
                      top: 15,
                      bottom: 30
                    },
                    color:'#ffffff',
                    font:{
                      family:'Noto Sans KR',
                      size:16,
                      weight:400
                    },
                    position:'bottom'
                  }
                },
                animation: {
                  duration: 0
                },
                responsive: false,
                scales: {
                  y:{
                    ticks: {color: "white", beginAtZero: true}
                  },
                  x: {
                    ticks: {color: "white", beginAtZero: true}
                  }
                }
              }
            });
            break;
          
          default :
            this.liveChart3 = new Chart(chart, {
              type: 'line',
              data: {
                labels: [],
                datasets: [{
                  pointRadius: 3,
                  pointBackgroundColor:['rgba(125, 136, 253, 1)'],
                  pointBorderColor:['rgba(125, 136, 253, 1)'],
                  backgroundColor:'rgba(125, 136, 253, 1)',
                  borderColor: 'rgba(125, 136, 253, 1)',
                  label: this.state.nummericSensorWithKor[idx],
                  data: [],
                }],
              },
              options: {
                maintainAspectRatio :false,
                plugins: {
                  legend: {
                    display:false
                  },
                  title:{
                    display:true,
                    text: this.state.nummericSensorWithKor[idx],
                    padding: {
                      top: 15,
                      bottom: 30
                    },
                    color:'#ffffff',
                    font:{
                      family:'Noto Sans KR',
                      size:16,
                      weight:400
                    },
                    position:'bottom'
                  }
                },
                animation: {
                  duration: 0
                },
                responsive: false,
                scales: {
                  y:{
                    ticks: {color: "white", beginAtZero: true}
                  },
                  x: {
                    ticks: {color: "white", beginAtZero: true}
                  }
                }
              }
            });
        }
      }
    })
    this.setState({ resetChart : false});
  }

  setChartData(){     //차트데이터세팅 (범주, 수치 센서들에 해당하는 값들을 가져온다.)
    this.setState({
      loading : true,
    });
    vehicleStatistics.getChartData(this.state.fileNameAndId, this.state.nummericSensor, this.state.categoricSensor)
    .then((response) => {
      this.setState({
        forChartData: response.data,
        loading:false,
        setData : true,
      }, () => {
        this.rangeSliderPlus();
      })
    })
    .catch((e) => {
      console.log(e);
    })
  }

  vehicleStatInfo(value) {    //우측상단의 차량상태 데이터세팅
    if(this.state.forChartData.length === this.state.i){
      return(<label>finish</label>);
    } else{
      switch(value){
        case 'engsta':
            const engsta = this.state.forChartData[this.state.i].ENGSTA;
            if(engsta === 0){
              return (<label>준비</label>);
            } else if(engsta === 1){
              return (<label>시동</label>);
            } else if(engsta === 2){
              return (<label>동작</label>)
            } else if(engsta === 3){
              return (<label>정지</label>)
            } else{
              return (<label>종료</label>);
            }
  
        case 'currttrans':
          const currttrans = this.state.forChartData[this.state.i].CURRTTRANS;
          if(currttrans === -1){
            return (<label>R</label>);
          }else if(currttrans === 0){
            return (<label>N</label>);
          }else{
            return (<label>D</label>);
          }
  
        case 'detailtrans':
          const detailtrans = this.state.forChartData[this.state.i].DETAILTRANS;
          return(<label>{detailtrans}</label>);
  
        case 'break':
          const Break = this.state.forChartData[this.state.i]._BREAK;
          if(Break === 0){
            return(<label>미작동</label>);
          }else{
            return (<label>작동</label>);
          }
        
        case 'cooltemp':
          const cooltemp = this.state.forChartData[this.state.i].COOLTEMP;
          return(<label>{cooltemp} ℃</label>);
        
        case 'accpedal':
          const accpedal = this.state.forChartData[this.state.i].ACCPEDAL;
          return(<label>{accpedal} %</label>);
  
        default :
          const fueltemp = this.state.forChartData[this.state.i].FUELTEMP;
          return(<label>{fueltemp}℃</label>);
      }
    }
  }

  openModal(){   //파일선택 팝업을 클릭하자마자 실행되는 함수
    this.setState({ 
      setModal : !this.state.setModal,
      isStart: false,
    }, () => {
      const changeText = document.getElementById('stopNStart');
      changeText.innerText = "▷";
    });
  }
  
  openModal2(){   //센서데이터 팝업을 클릭하자마자 실행되는 함수
    this.setState({ 
      setModal2: !this.state.setModal2,
      isStart: false,
    }, () => {
      
      const changeText = document.getElementById('stopNStart');
      changeText.innerText = "▷";
    });
  }
  
  changeModal(res) {    //modal컴포넌트에서 false를 받아와 false일때 오픈되는 파일선택화면의 setModal을 바꿔준다.
    this.setState({
      setModal: res,
    }, () => {
      console.log(this.state.setModal);
    });
  }
  
  changeModal2(res){    //modal컴포넌트에서 false를 받아와 false일때 실행되는 센서선택팝업의 setModal2를 바꿔준다.
    this.setState({
      setModal2: res,
    }, () => {
      console.log(this.state.setModal2);
    });
  }

  forInputFileName(res){  //팝업창에서 가져온 파일이름과 SDAID를 저장하는 함수
    if(res !== null){
    this.setState({
      fileNameAndId: res,
      i: 0,
      resetChart: true,
      isStart: false,
      // changeSensorForChart: true,
    }, () => {
        if(this.state.fileNameAndId.length > 0 
          && this.state.nummericSensor.length > 0 
          && this.state.categoricSensor.length > 0){
            this.setChartData();
        }
    });
    
    
      this.props.selectEachInfo(res[0]);
    }else{
      this.setState({
        loading : false,
      })
    }
  }

  forUseSensor(res){    //센서선택팝업창에서 가져온 데이터를 저장하는 함수  
    if(res !== null){
      if(this.state.nummericSensor.length > 1){
        this.destroyChart();
      }
      this.setState({
        nummericSensor: res[0],
        categoricSensor: res[1],
        nummericSensorWithKor: res[2],
        categoricSensorWithKor: res[3],
        i: 0,
        resetChart: true,
        changeSensorForChart: true,
      }, () =>{
        this.myLiveChart.destroy();
        this.drawExtraLiveChart();
        this.drawingChart();
  
        if(this.state.fileNameAndId.length > 0 
          && this.state.nummericSensor.length > 0 
          && this.state.categoricSensor.length > 0){
            this.setChartData();
        }
      });
    }
  }
  progressBar(value){   //게이지차트 우측의 프로그래스바

    const containerStyles = {
      height: 50,
      width: '200px',
      backgroundColor: "#e0e0de",
      borderRadius: 50,
      margin: 50,
      transform: "rotate(270deg)"
    };
    const labelStyles = {
      padding: 5,
      color: 'white',
      fontWeight: 'bold'
    };
    
    if(value === 'datanull' || this.state.forChartData.length === this.state.i){
      const fillerStyles = {
        height: '100%',
        width: `${0}`,
        backgroundColor: '#000000',
        borderRadius: 'inherit',
        textAlign: 'right',
      };
      return(
        <div style={containerStyles}>
          <div style={fillerStyles}>
            <span style={labelStyles}>{`${0}%`}</span>
          </div>
        </div>
      )
    }
    switch(value){
      case 'fuel' :
        const fuel = this.state.forChartData[this.state.i].FUEL;
        const fillerStyles = {
          height: '100%',
          width: `${fuel}%`,
          backgroundColor: '#000000',
          borderRadius: 'inherit',
          textAlign: 'right',
        };
        return(
          <div style={containerStyles}>
            <div style={fillerStyles}>
              <span style={labelStyles}>{`${fuel}%`}</span>
            </div>
          </div>
        )

      default :
        const outtemp = this.state.forChartData[this.state.i].OUTTEMP;
        const containerStyles2 = {
          height: 50,
          width: '200px',
          backgroundColor: "#e0e0de",
          borderRadius: 50,
          margin: 50,
          transform: "rotate(270deg)"
        };
        const fillerStyles2 = {
          height: '100%',
          width: `${outtemp+30}%`,
          backgroundColor: '#000000',
          borderRadius: 'inherit',
          textAlign: 'right',
        };
        const labelStyles2 = {
          padding: 5,
          color: 'white',
          fontWeight: 'bold'
        };
        return(
          <div style={containerStyles2}>
            <div style={fillerStyles2}>
              <span style={labelStyles2}>{`${outtemp}℃`}</span>
            </div>
          </div>
        )
    }
  }

  categoricLabel(){
    let idx = this.state.i
    if(this.state.i === this.state.forChartData.length){
      return(
        <label>finish</label>
      )
    }else{
      return(
        <Categoric idx={idx} chartData={this.state.forChartData} data={this.state.categoricSensor} />
      );
    }
    
  }

  destroyChart(){   //여분의 차트를 그려줄때 삭제하는 함수
    this.state.nummericSensor.forEach((element, idx) => {
      if(idx !== 0){
        switch(idx){
          case 1: 
            this.liveChart0.destroy();
            break;

          case 2:
            this.liveChart1.destroy();
            break;
          
          case 3:
            this.liveChart2.destroy();
            break;
          
          default :
            this.liveChart3.destroy();
        }
      }
    })
  }

  categoricSensorIsChange(){    //범주형센서가 바뀌었을때 실행
    if(this.state.categoricSensor.length > 0 ){
      if(this.state.changeSensorForChart){
        return ( 
          <label className="btnn">-</label>
        );
      }else{
        return( 
          <Categoric idx={this.state.i} chartData={this.state.forChartData} data={this.state.categoricSensor} data2={this.state.categoricSensorWithKor} />
        );
      }
    } else{
      return ( 
        <label className="btnn">-</label>
      );
    }
  }
  //속도 느리게
  slowChart() {
    let speed = document.getElementById('speed');
    
    switch(speed.innerText){
      case "x1": 
      speed.innerText = "x0.5";
      this.setState({
        chartSpeed: 1000,
      }, () => {
        clearInterval(this.interval);
        this.interval = setInterval(this.liveStream, this.state.chartSpeed);
      })
      break;
    
      case "x2":
        speed.innerText = "x1";
        this.setState({
          chartSpeed: 100,
        }, () => {
          clearInterval(this.interval);
          this.interval = setInterval(this.liveStream, this.state.chartSpeed);
        })
        break;

      default:

    }
  }
  //속도 빠르게
  fastChart(){
    let speed = document.getElementById('speed');
    
    switch(speed.innerText){
      case "x0.5":
        speed.innerText = "x1";
        this.setState({
          chartSpeed: 100,
        }, () => {
          clearInterval(this.interval);
          this.interval = setInterval(this.liveStream, this.state.chartSpeed);
        })
        break;

      case "x1":
        speed.innerText = "x2";
        this.setState({
          chartSpeed: 50,
        }, () => {
          clearInterval(this.interval);
          this.interval = setInterval(this.liveStream, this.state.chartSpeed);
        })
        break;
      
      case "x3": 
        break;

      default:
    }
  }

  render() {
    let selectedFile = null;
    let fileNameAndId = this.state.fileNameAndId;
    if(fileNameAndId !== null){
      if(fileNameAndId[1]){
        let year = fileNameAndId[1].substring(0,4);
        let month = fileNameAndId[1].substring(4,6);
        let day = fileNameAndId[1].substring(6,8);
        let hour = fileNameAndId[1].substring(8,10);
        let miniute = fileNameAndId[1].substring(10,12);
        let seconds = fileNameAndId[1].substring(12,14);
        selectedFile = year + "/" + month + "/" + day + " " + hour + ":" + miniute + ":" + seconds;
      }
    }
    
    return (
      <div className="container"  disabled={this.state.loading}>
         {this.state.loading && (
            <div className="d-flex justify-content-center loading-box">
                <div className="spinner-border loading-in" role="status">
                    <span className="sr-only">Loading...</span>
                </div>
             </div>
           )}
        <Container fluid className="item">
          <Row className="min">
            <div className="item1 col-3">
              <div className="jumbotron">
                차륜형 장갑차 장비 제원
              </div>
              <div className="contents06" >
               
                <Col className="item11">
                 
                  <table className="table02 info_table">
                    <tbody>
                      <tr>
                        <td colSpan={2} className="image"><span></span></td>
                      </tr>
                      <tr>
                        <td>타입</td>
                        <td>{this.props.eachInfo.sdaid}</td>
                      </tr>
                      <tr>
                        <td>부대명</td>
                        <td>{this.props.eachInfo.divs} {this.props.eachInfo.brgd} {this.props.eachInfo.bn}</td>
                      </tr>
                      <tr>
                        <td>등록번호</td>
                        <td>{this.props.eachInfo.rgstno}</td>
                      </tr>
                      <tr>
                        <td>일련번호</td>
                        <td>{this.props.eachInfo.sn}</td>
                      </tr>
                      <tr>
                        <td>제작일자</td>
                        <td>{this.props.eachInfo.mfdt}</td>
                      </tr>
                      <tr>
                        <td>취득일자</td>
                        <td>
                          {this.props.eachInfo.acqdt}</td>
                      </tr>
                      <tr>
                        <td>파일명</td>
                        <td>
                        {
                        this.state.fileNameAndId&&
                        this.state.fileNameAndId[1]
                        }
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </Col>
                <Col className="item12" >
                  <div className="item_top">
                    <button className="stopnstart playbtn" id="stopNStart" onClick={this.stopChart}>▶</button>
                    <input 
                      id="slider"
                      type="range"   
                      defaultValue = '0'
                      max='100'
                      list="stepList"
                    />
                  </div>
                  <div className="item_bottom">
                    <button className="playbtn" id="restart" onClick={this.restart}>■</button>
                    <button className="playbtn" id="reverse" onClick={this.reverse}>◀</button>
                    <div className="slider-control">
                      <button className="playbtn" onClick={this.slowChart}>-</button>
                      <button className="playbtn" id="speed" value="x1" disabled={true}>x1</button>
                      <button className="playbtn" onClick={this.fastChart}>+</button>
                    </div>
                  </div>
                </Col>
              </div>
            </div>
            <div className="contents00 col-9">
              <div className="item2" md="7">
                <Col className="item21">
                  <div className="jumbotron">
                    차량 상태 정보
                    <div className="dateModalButton">
                    <p className="info_date" style={{display:'inline-block'}}>
                      {selectedFile !== null && selectedFile}</p>
                    <button className="pop-btn" onClick={this.openModal}>mdl1</button>
                    </div>
                    {
                      this.state.setModal === true
                      ? this.props.eachInfo.sdaid !== null 
                        ? <OpenModal name={this.props.eachInfo.sdaid} modalDiv="choiceDate" data={this.props.eachInfo} 
                        modalFunc={this.changeModal}
                        fileIdFunc={this.forInputFileName} 
                        />
                        : <OpenModal name="noName" />
                      : null
                    }
                  </div>
                  <div className="contents07">
                    <div className="con-top row">
                        <div className="vehicleDtlInfo col-2">
                          <label className="ve-title">엔진상태</label>
                          {
                            this.state.forChartData.length > 0
                            ? this.vehicleStatInfo('engsta')
                            : <label className="ve-data blue">-</label>
                          }
                          <label className="ve-title">현재변속</label>
                          {
                            this.state.forChartData.length > 0
                            ? this.vehicleStatInfo('currttrans')
                            : <label className="ve-data green">-</label>
                          }
                          <label className="ve-title">상세변속</label>
                          {
                            this.state.forChartData.length > 0
                            ? this.vehicleStatInfo('detailtrans')
                            : <label className="ve-data red">-</label>
                          }
                        </div>

                        <div className="vehicleDtlInfo col-7">
                          <div id="gaugeChart"/>
                        </div>

                        <div className="vehicleDtlInfo col-3">
                          <div className="graphes">
                            <div className="graph01">
                              {
                                this.state.forChartData.length > 0
                                ? this.progressBar('fuel')
                                : this.progressBar('datanull')
                              }
                            </div>
                            <div className="graph02">
                              {
                                this.state.forChartData.length > 0
                                ? this.progressBar('outtemp')
                                : this.progressBar('datanull')
                              }
                            </div>
                          </div>
                        </div>
                    </div>
                    <div className="con-bottom">
                      <Row>
                        <div className="ve-label">
                          <label className="ve-title"> 브레이크</label>
                          {
                            this.state.forChartData.length > 0
                            ? this.vehicleStatInfo('break')
                            : <label className="ve-btn green">-</label>
                          }
                        </div>
                        <div className="ve-label">
                          <label className="ve-title"> 냉각수온도</label>
                          {
                            this.state.forChartData.length > 0
                            ? this.vehicleStatInfo('cooltemp')
                            : <label className="ve-btn green">-</label>
                          }
                        </div>
                        <div className="ve-label">
                          <label className="ve-title"> 가속페달</label>
                          {
                            this.state.forChartData.length > 0
                            ? this.vehicleStatInfo('accpedal')
                            : <label className="ve-btn yellow">-</label>
                          }
                        </div>
                        <div className="ve-label">
                          <label className="ve-title"> 연료온도</label>
                          {
                            this.state.forChartData.length > 0
                            ? this.vehicleStatInfo('fueltemp')
                            : <label className="ve-btn blue">-</label>
                          }
                        </div>
                      </Row>
                    </div>
                  </div>
                </Col>
                <Col className="item22 mt30" >
                  <div className="jumbotron">
                    차량센서정보
                    <button className="pop-btn" onClick={this.openModal2} >mdl2</button>
                    {
                      this.state.setModal2 === true
                      ? <OpenModal name="choiceSensor" modalDiv="choiceSensor" modalFunc={this.changeModal2} sensorFunc={this.forUseSensor}/>
                      : null
                    }
                  </div>
                  <div className="contents07">
                    <Col className="item23" >
                      <Row>
                      { 
                        this.categoricSensorIsChange()
                      }
                      </Row>
                    </Col>
                  </div>
                  <div className="contents07 mt24">
                    <Col className="item24">
                      <canvas id="liveChart" className="livechart"  ref={this.liveChartRef} width="900vw" height="300vw" />
                      <div>
                        {
                          this.state.nummericSensor && 
                          this.state.nummericSensor.map((element, idx) => {
                            let id = 'liveChart' + idx;
                            if(idx !== 0){
                              switch(idx){
                                case 1:
                                  return(
                                    <canvas id={id} className="livechart"  key={element}  width="900vw" height="300vw"/>
                                  );
                                case 2:
                                  return(
                                    <canvas id={id} className="livechart"  key={element} width="900vw" height="300vw" />
                                  );
                                  
                                case 3:
                                  return(
                                    <canvas id={id} className="livechart"  key={element}  width="900vw" height="300vw" />
                                  );

                                default:
                                  return(
                                    <canvas id={id} className="livechart"  key={element} width="900vw" height="300vw" />
                                    );
                                }
                              }
                            })
                          }
                        </div>
                    </Col>
                  </div>
               </Col>
              </div>
            </div>
          </Row>
        </Container>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  const {user} = state.auth;
  return {
    eachInfo: state.tutorials,
    user,
  };
};

export default connect(mapStateToProps, {
  selectEachInfo,
})(searchEachInfo);
