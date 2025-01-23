import { Button } from '@/components/ui/button';
import React from 'react';
import AssetTable from './AssetTable';
import StockChart from './StockChart';
import { Avatar, AvatarImage } from '@/components/ui/avatar';
import { DotIcon } from '@radix-ui/react-icons';
import { CrossIcon, MessageCircle } from 'lucide-react';
import { Input } from '@/components/ui/input';

const Home = () => {
  const [category, setCategory] = React.useState("all");
  const [inputValue,setInputValue]=React.useState("")

  const [isBotRelease,setIsBotRelease] = React.useState(false)
  const handleBotRelease=()=>setIsBotRelease(!isBotRelease)

  const handleCategory = (value) => {
    setCategory(value);
  };

  const handleChange=(e)=>{
    setInputValue(e.target.value)
  }

  const handleKeyPress =(event)=>{
    if(event.key=="Enter"){
      console.log(inputValue)
    }
    setInputValue("")
  }

  return (
    <div className="relative">
      <div className="lg:flex">
        {/* Left Panel */}
        <div className="lg:w-[50%] lg:border-right">
          <div className="p-3 flex items-center gap-4">
            <Button
              onClick={() => handleCategory("all")}
              variant={category === "all" ? "default" : "outline"}
              className="rounded-full"
            >
              All
            </Button>
            <Button
              onClick={() => handleCategory("top-50")}
              variant={category === "top-50" ? "default" : "outline"}
              className="rounded-full"
            >
              Top-50
            </Button>
            <Button
              onClick={() => handleCategory("topGainers")}
              variant={category === "topGainers" ? "default" : "outline"}
              className="rounded-full"
            >
              Top Gainers
            </Button>
            <Button
              onClick={() => handleCategory("topLosers")}
              variant={category === "topLosers" ? "default" : "outline"}
              className="rounded-full"
            >
              Top Losers
            </Button>
          </div>
          <AssetTable />
        </div>

        {/* Right Panel */}
        <div className="hidden lg:block lg:w-[50%] p-5">
          <StockChart />

          <div className="flex gap-5 items-center">
            <Avatar>
              <AvatarImage src="https://cdn.pixabay.com/photo/2021/05/09/13/10/finance-6240949_960_720.png" />
            </Avatar>
            <div>
              <div className="flex items-center gap-2">
                <p>ETH</p>
                <DotIcon className="text-gray-400" />
                <p className="text-gray-400">Ethereum</p>
              </div>
              <div className="flex items-end gap-2">
                <p className="text-xl font-bold">5656</p>
                <p className="text-red-600">
                  <span>-1313234543243.2342</span>
                  <span>(-0.23423%)</span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Chatbot Section */}
      <section className="absolute bottom-5 right-5 z-40 flex flex-col justify-end items-end gap-2">
        {/* Chatbot Box */}
       {isBotRelease && <div className="rounded-md w-[20rem] md:w-[25rem] lg:w-[25rem] h-[70vh] bg-slate-900">
          <div className="flex justify-between items-center border-b px-6 h-[12%]">
            <p>ChatBot</p>
            <Button onClick={handleBotRelease} variant="ghost" size="icon">
              <CrossIcon />
            </Button>
          </div>

          <div className="h-[76%] flex flex-col overflow-y-auto gap-5 px-5 py-2 scroll-container">
            <div className="self-start pb-5 w-auto">
              <div className="justify-end self-end px-5 py-2 rounded-md bg-slate-800 w-auto">
                <p>Hi, Ram</p>
                <p>Hello, I am a Java developer</p>
                <p>Like</p>
              </div>
            </div>

            {[1, 1, 1, 1].map((item, i) => (
              <div
                key={i}
                className={`${
                  i % 2 === 0 ? "self-start" : "self-end"
                } pb-5 w-auto`}
              >
                <div
                  className={`px-5 py-2 rounded-md ${
                    i % 2 === 0 ? "bg-slate-800" : "bg-slate-700"
                  } w-auto`}
                >
                  <p>{i % 2 === 0 ? "Prompt: Who are you?" : "Ans: Ram Arora"}</p>
                </div>
              </div>
            ))}
          </div>
          <div className='h-[12%] border-t'>
            <Input className='w-full h-full order-none outline-none' placeholder="write prompt" onChange={handleChange}
            value={inputValue}
            onKeyPress={handleKeyPress}/>
          </div>
        </div>}

        {/* Chatbot Button */}
        <div className="relative w-[10rem] cursor-pointer group">
          <Button onClick={handleBotRelease} className="w-full h-[3rem] gap-2 items-center">
            <MessageCircle
              size={30}
              className="fill-[#1e293b] -rotate-90 stroke-none group-hover:fill-[#1a1a1a]"
            />
            <span className="text-2xl">ChatBot</span>
          </Button>
        </div>
      </section>
    </div>
  );
};

export default Home;
