import { Button } from "@/components/ui/button";
import { DialogClose } from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import React from "react";
import { useForm } from "react-hook-form";

const Signup = () => {
  const form = useForm({
    resolver: "",
    defaultValues: {
      fullName: "",
      email: "",
      password: "",
      
    },
  });
  const onSubmit = (data) => {
    console.log(data);
  };
  return (
    <div className="px-10 py-2">
      <h1 className="text-xl font-bold text-center pb-3">Create new account</h1>  
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
          <FormField
            control={form.control}
            name="fullName"
            render={({ field }) => (
              <FormItem>
                <FormLabel>fullName : </FormLabel>
                <FormControl>
                  <Input 
                  
                  className='border w-full border-gray-700 p-5'
                  placeholder="yash sharma" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Email : </FormLabel>
                <FormControl>
                  <Input 
                  
                  className='border w-full border-gray-700 p-5'
                  placeholder="yash@gmail.com" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem>
                 <FormLabel>Password : </FormLabel>
                <FormControl>
                  <Input 
                  className='border w-full border-gray-700 p-5'
                  placeholder="**********" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
         
     
          <Button type='submit' className='w-full py-5'>
            Submit
          </Button>
         
          
        </form>
      </Form>
    </div>
  );
};

export default Signup;
