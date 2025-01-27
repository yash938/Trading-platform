import { Button } from '@/components/ui/button'
import React, { useState } from 'react'
import ReactApexChart from 'react-apexcharts'

const timeSeries=[
    {
        keyword:"DIGITAL_CURRENCY_DAILY",
        key:"Time Series (Daily)",
        lable:"1 Day",
        value:1
    },
    {
        keyword:"DIGITAL_CURRENCY_WEEKLY",
        key:"Weekly Time Series",
        lable:"1 Week",
        value:7
    },
    {
        keyword:"DIGITAL_CURRENCY_WEEKLY",
        key:"Monthly Time Series",
        lable:"1 Month",
        value:30
    },
    
]
const StockChart = () => {

    const [activeLable,setActiveLable]=useState("1 Day")
    const series=[
        {
            data: [
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
                [1234567543323,67654.5564322567],
            ]
        }
    ]

    const options={
        chart:{
            id:"area-datetime",
            type:"area",
            height:450,
            zoom:{
                autoScaleYaxis:true
            }
        },
        dataLabels:{
            enabled:false
        },
        xaxis:{
            type:"datetime",
            tickAmount:6
        },
        colors:["#758AA2"],
        markers:{
            colors:["#fff"],
            strokeColor:"#fff",
            size:4,
            strokeWidth:1,
            style:"hollow"
        },
        tooltip:{
            theme:"dark"
        },
        fill:{
            type:"gradient",
            gradient:{
                shadeIntensity:1,
                opacityFrom:0.7,
                opacityTo:0.9,
                stops:[0,100]
            }
        },
        grid:{
            borderColor:"#47535E",
            strokeDashArray:4,
            show:true
        }
    }
    const handleActiveLable=(value)=>{
        setActiveLable(value)
    }
  return (
    <div>
        <div className='space-x-3'>
        {timeSeries.map((item)=><Button 
        variant={activeLable==item.lable?"":"outline"}
        onClick={()=>handleActiveLable(item.lable)} key={item.lable} >
            {item.lable}
        </Button>)}
        </div>
      <div id='chart-timeline'>
        <ReactApexChart options={options} series={series} height={450} type='area'/>
      </div>
    </div>
  )
}

export default StockChart
