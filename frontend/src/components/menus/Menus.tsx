import React from 'react';
import {List} from "@material-ui/core";
import InsertDriveFileIcon from '@material-ui/icons/InsertDriveFile';
import AssignmentIcon from '@material-ui/icons/Assignment';
import FlightTakeoffIcon from '@material-ui/icons/FlightTakeoff';
import NoteAddIcon from '@material-ui/icons/NoteAdd';
import AssignmentTurnedInIcon from '@material-ui/icons/AssignmentTurnedIn';
import ClearAllOutlinedIcon from '@material-ui/icons/ClearAllOutlined';
import LibraryBooksIcon from '@material-ui/icons/LibraryBooks';
import AppMenuItem from "./AppMenuItem";

const appMenuItems = [
  {
    name: '문서 관리',
    icon: <InsertDriveFileIcon/>,
    items: [
      {
        name: '신규 문서 생성',
        link: '/documents/create',
        icon: <NoteAddIcon/>
      },
      {
        name: '진행 문서함',
        link: '/documents/outbox',
        icon: <LibraryBooksIcon/>
      },
      {
        name: '결재 문서함',
        link: '/documents/inbox',
        icon: <AssignmentTurnedInIcon/>
      },
      {
        name: '완료 문서함',
        link: '/documents/archive',
        icon: <ClearAllOutlinedIcon/>
      },
    ],
  },
  {
    name: '휴가 관리',
    icon: <AssignmentIcon/>,
    items: [
      {
        name: '휴가 신청',
        link: '/vacations/create',
        icon: <FlightTakeoffIcon/>
      },
    ],
  },
]

const MainMenus = () => {
  return (
    <>
      <List component="nav" disablePadding>
        {appMenuItems.map((item, index) => (
          <AppMenuItem {...item} key={index}/>
        ))}
      </List>
    </>
  );
}

export default MainMenus;
