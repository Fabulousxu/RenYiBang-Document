// service/user.js

export const getUserProfile = async () => {
    // Simulating a backend call with a static response
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                user: {
                    user_id: 1,
                    user_nickname: 'John Doe',
                    user_admin: 1,
                    user_avatar: 'https://randomuser.me/api/portraits/men/1.jpg',
                    user_intro: 'This is John Doe, an admin user.',
                    user_rating: 85, // 8.5 in frontend
                    user_balance: 10000, // 100.00元 in frontend
                    user_following: 256,
                },
            });
        }, 1000);
    });

    // Uncomment and modify the code below to fetch data from the backend once available
    /*
    try {
      const response = await fetch('/api/user/profile');
      const result = await response.json();
      return result;
    } catch (error) {
      console.error('Failed to fetch user profile:', error);
      throw error;
    }
    */
};

export const getUserTasks = async () => {
    // Simulating a backend call with a static response
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                tasks: [
                    {
                        id: '1',
                        title: 'Task 1',
                        description: 'Description of task 1',
                        status: '进行中',
                    },
                    {
                        id: '2',
                        title: 'Task 2',
                        description: 'Description of task 2',
                        status: '已完成',
                    },
                    {
                        id: '3',
                        title: 'Task 3',
                        description: 'Description of task 3',
                        status: '已取消',
                    },
                ],
            });
        }, 1000);
    });

    // Uncomment and modify the code below to fetch data from the backend once available
    /*
    try {
      const response = await fetch('/api/user/tasks');
      const result = await response.json();
      return result;
    } catch (error) {
      console.error('Failed to fetch user tasks:', error);
      throw error;
    }
    */
};
