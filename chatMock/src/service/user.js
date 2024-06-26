// service/user.js

export const getUserProfile = async () => {
    // Simulating a backend call with a static response
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                user: {
                    avatar: 'https://randomuser.me/api/portraits/men/1.jpg',
                    name: 'John Doe',
                    email: 'john.doe@example.com',
                    username: 'johndoe',
                    role: 'admin',
                    createdAt: '2021-01-01',
                    lastLogin: '2024-06-26',
                    status: 'active',
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
