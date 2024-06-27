import {Navigate, Route, Routes} from 'react-router-dom';
import TaskPage from "./page/task_page";
import ServicePage from "./page/service_page";
import HelpPage from "./page/help_page";
import MessagePage from "./page/message_page";
import ProfilePage from "./page/profile_page";
import OrderPage from "./page/order_page";
import OrderDetailPage from "./page/order_datail_page";
import IssuePage from './page/issue_page';
import TaskDetailPage from "./page/task_detail_page";
import ServiceDetailPage from "./page/service_detail_page";

export default function App() {
  return (
    <Routes>
      <Route index={true} element={<Navigate to='/task'/>}/>
      <Route path='/task' element={<TaskPage/>}/>
      <Route path='/task/:id' element={<TaskDetailPage/>}/>
      <Route path='/service' element={<ServicePage/>}/>
      <Route path='/service/:id' element={<ServiceDetailPage/>}/>
      <Route path='/help' element={<HelpPage/>}/>
      <Route path='/order' element={<OrderPage/>}/>
      <Route path='/order/:id' element={<OrderDetailPage/>}/>
      <Route path='/message' element={<MessagePage/>}/>
      <Route path='/profile/:id' element={<ProfilePage/>}/>
      <Route path='/issue' element={<IssuePage/>}/>
    </Routes>
  );
}