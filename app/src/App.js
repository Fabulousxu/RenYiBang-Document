import './App.css';
import {Navigate, Route, Routes} from 'react-router-dom';
import TaskPage from "./page/task";
import ServicePage from "./page/service";
import HelpPage from "./page/help";
import MessagePage from "./page/message";
import ProfilePage from "./page/profile";

export default function App() {
  return (
    <Routes>
      <Route index={true} element={<Navigate to='/task'/>}/>
      <Route path='/task' element={<TaskPage/>}/>
      <Route path='/service' element={<ServicePage/>}/>
      <Route path='/help' element={<HelpPage/>}/>
      <Route path='/message' element={<MessagePage/>}/>
      <Route path='/profile' element={<ProfilePage/>}/>
    </Routes>
  );
}