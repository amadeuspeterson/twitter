package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;

public class MainPresenterUnitTest {
    private MainPresenter mainPresenterSpy;
    private MainPresenter.MainView mockView;
    private FeedService mockFeedService;
    private Cache mockCache;

    @BeforeEach
    public void beforeEach(){
        mockView = Mockito.mock(MainPresenter.MainView.class);
        mockFeedService = Mockito.mock(FeedService.class);
        mockCache = Mockito.mock(Cache.class);
        Cache.setInstance(mockCache);
        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));
        Mockito.doReturn(mockFeedService).when(mainPresenterSpy).getFeedService();
    }

    @Test
    public void testPostStatusSuccess() {
        Answer<Void> success = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostStatusObserver observer = invocation.getArgument(2, MainPresenter.PostStatusObserver.class);
                observer.handleSuccess();
                return null;
            }
        };

        Mockito.doAnswer(success).when(mockFeedService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("hello world");
        Mockito.verify(mockView).displayInfoMessage("Posting Status...");

        Mockito.verify(mockView).clearInfoMessage();
        Mockito.verify(mockView).clearErrorMessage();
        Mockito.verify(mockView).displayInfoMessage("Successfully posted status.");
    }

    @Test
    public void testPostStatusFailure(){
        Answer<Void> failure = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostStatusObserver observer = invocation.getArgument(2, MainPresenter.PostStatusObserver.class);
                observer.handleFailure("error message");
                return null;
            }
        };

        Mockito.doAnswer(failure).when(mockFeedService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("test");
        Mockito.verify(mockView).displayInfoMessage("Posting Status...");

        Mockito.verify(mockView).clearInfoMessage();
//        Mockito.verify(mockView).clearErrorMessage();
        Mockito.verify(mockView).displayInfoMessage("error message");
    }

    @Test
    public void testPostStatusException(){
        Exception ex = new Exception();
        Answer<Void> exception = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostStatusObserver observer = invocation.getArgument(2, MainPresenter.PostStatusObserver.class);
                observer.handleException(ex);
                return null;
            }
        };

        Mockito.doAnswer(exception).when(mockFeedService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("test");
        Mockito.verify(mockView).displayInfoMessage("Posting Status...");

        Mockito.verify(mockView).clearInfoMessage();
//        Mockito.verify(mockView).clearErrorMessage();
        Mockito.verify(mockView).displayErrorMessage("Failed to post status because of exception: " + ex.getMessage());
    }
}
