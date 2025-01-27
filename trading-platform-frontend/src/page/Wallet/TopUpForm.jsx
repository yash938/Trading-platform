import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { DotFilledIcon } from "@radix-ui/react-icons";
import React from "react";

const TopUpForm = () => {
  const [amount, setAmount] = React.useState("");
  const [payment, setPayment] = React.useState("RAZORPAY");
  const handleChange = (e) => {
    setAmount(e.target.value);
  };

  const handleSubmit=()=>{
    console.log(amount,payment)
  }

  const handlePaymentMethodChange = (value) => {
    setPayment(value);
  };
  return (
    <div className="pt-10 space-y-5 ">
      <div>
        <h1 className="pb-1">Enter Amount</h1>
        <Input
          onChange={handleChange}
          value={amount}
          className="py-7 text-lg"
          placeholder="$9999"
        />
      </div>

      <h1 className="pb-1">Select payment method</h1>
      <RadioGroup
        onValueChange={(value) => handlePaymentMethodChange(value)}
        className="flex"
        defaultValue="RAZORPAY"
      >
        <div className="flex items-center space-x-2 border p-3 px-5 rounded-md">
          <RadioGroupItem
            icon={DotFilledIcon}
            className="h-9 w-9"
            value="RAZORPAY"
            id="r1"
          />
          <Label htmlFor="r1">
            <div className="bg-white rounded-md px-5 py-2 w-32  ">
              <img className="h-12"
                src="https://cdn.iconscout.com/icon/free/png-512/free-razorpay-logo-icon-download-in-svg-png-gif-file-formats--payment-gateway-brand-logos-icons-1399875.png?f=webp&w=256"
                alt=""
              />
            </div>
          </Label>
        </div>
        <div className="flex items-center space-x-2 border p-3 px-5 rounded-md">
          <RadioGroupItem
            icon={DotFilledIcon}
            className="h-9 w-9"
            value="STRIPE"
            id="r1"
          />
          <Label htmlFor="r1">
            <div className="bg-white rounded-md px-5 py-2 w-32  ">
              <img className="h-12"
                src="https://cdn.iconscout.com/icon/free/png-512/free-stripe-logo-icon-download-in-svg-png-gif-file-formats--technology-social-media-vol-6-pack-logos-icons-2945188.png?f=webp&w=256"
                alt=""
              />
            </div>
          </Label>
        </div>
      </RadioGroup>
      <Button onClick={handleSubmit} className='w-full py-7 '>Submit</Button>
    </div>
    
  );
};

export default TopUpForm;
