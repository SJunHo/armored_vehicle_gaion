import React, { Component, useState } from 'react';
import './App.css';
import '../node_modules/react-vis/dist/style.css';
import {XYPlot, VerticalGridLines, HorizontalGridLines, LineMarkSeries, XAxis, YAxis, VerticalBarSeries, HorizontalBarSeries, ArcSeries, VerticalBarSeriesCanvas, DiscreteColorLegend} from 'react-vis';
import { RadialChart } from 'react-vis';
import { Row } from 'react-bootstrap';
import { Col } from 'react-bootstrap';

export function TestChart() {
  
    const [useCanvas, setUseCanvas] = useState(false);
    const BarSeries = useCanvas ? VerticalBarSeriesCanvas : VerticalBarSeries;
      
    const LineData = [
      {x: 0, y: 8},
      {x: 1, y: 5},
      {x: 2, y: 4},
      {x: 3, y: 9},
      {x: 4, y: 1},
      {x: 5, y: 7},
      {x: 6, y: 6},
      {x: 7, y: 3},
      {x: 8, y: 2},
      {x: 9, y: 0}
    ];

    const BarData = [
      {x: 'A', y: 6, color:'red'},
      {x: 'B', y: 8, color:'blue'},
      {x: 'C', y: 10, color:'white'}
    ]
const [index, setIndex] = useState('#2596be')
   
    return (
      <>
      <Row>
        <Col>
     
        <XYPlot height={300} width={300}>
          
          <XAxis/>
          <YAxis/>
          <LineMarkSeries data={LineData} 
           
          lineStyle={{stroke: '#0b8d9b'}}
        markStyle={{stroke: '#0b8d9b'}} />

        </XYPlot>
      
      </Col>
      <Col>
      <XYPlot
          className="clustered-stacked-bar-chart-example"
          xType="ordinal"
          stackBy="y"
          width={300}
          height={300}
          color='#0b8d9b'
            >
          <XAxis />
          <YAxis/>
          <BarSeries
            barWidth={0.8}
            
            data={[
              {x: '1', y: 15,},
              {x: '2', y: 15,},
              {x: '3', y: 15,},
              {x: '4', y: 2}
            ]}
            
          />
          </XYPlot>
      </Col>
      <Col>
      <RadialChart data={[{angle:7,  },    {angle: 1},  {angle: 2,}]} 
  width={300}  height={300}

   />
          </Col>
      </Row>
      <Row>
        <Col>
        <XYPlot width={300} height={300} stackBy="y" barwidth={0.8}>
        
        <XAxis />
        <YAxis />
        
        <BarSeries barWidth={0.8}
          color="#205b49"
          data={[{ x: 1, y: 5 }, { x: 2, y: 1 }, { x: 3, y: 3 }, {x:4, y:1}, {x:5, y:2}]}
        />
        <BarSeries barWidth={0.8}
          color="#2a7963"
          data={[{ x: 1, y: 5 }, { x: 2, y: 1 }, { x: 3, y: 3 }, {x:4, y:1},{x:5, y:2}]}
        />
      </XYPlot></Col>
      <Col>
        <XYPlot width={300} height={300} stackBy="y">
        
        <XAxis />
        <YAxis />
        
        <BarSeries barWidth={0.8}
          color="#205b49"
          data={[{ x: 1, y: 5 }, { x: 2, y: 1 }, { x: 3, y: 1 }, {x:4, y:1}, {x:5, y:1}]}
        />
        <BarSeries barWidth={0.8}
          color="#2a7963"
          data={[{ x: 1, y: 5 }, { x: 2, y: 1 }, { x: 3, y: 1 }, {x:4, y:1}, {x:5, y:1}]}
        />
      </XYPlot></Col>
      <Col>
        <XYPlot width={300} height={300} stackBy="y">
        
        <XAxis />
        <YAxis />
        
        <BarSeries barWidth={0.8}
          color="#205b49"
          data={[{ x: 1, y: 10 }, { x: 2, y: 11 }, { x: 3, y: 10 }, {x:4, y:9}, {x:5, y:12}, { x: 6, y: 11 }, { x: 7, y: 10 }, { x: 8, y: 12 }, {x:9, y:9}, {x:10, y:10}]}
        />
        <BarSeries barWidth={0.8}
          color="#2a7963"
          data={[{ x: 1, y: 10 }, { x: 2, y: 11 }, { x: 3, y: 10 }, {x:4, y:9}, {x:5, y:12}, { x: 6, y: 11 }, { x: 7, y: 10 }, { x: 8, y: 12 }, {x:9, y:9}, {x:10, y:10}]}
        />
      </XYPlot></Col>
      </Row>
      <Row>
        <Col>
        <RadialChart data={[{angle:7, }]} width={300}
  height={300} />
  </Col>
  <Col>
  <RadialChart data={[{angle:7, },
    ]} width={300}
  height={300} />
        </Col>
      </Row>
      </>
    );
  }


