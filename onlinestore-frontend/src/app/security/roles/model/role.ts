import {User} from '../../../users/model/user';
import {Privilege} from '../../privileges/model/privilege';

export class Role {
  id: number;
  name: string;
  privilegeList: Privilege[];
  userList: User[];
}